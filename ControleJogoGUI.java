import javax.swing.*;
import java.awt.*;

public class ControleJogoGUI {
    public static final int NUM_CASAS = 24;
    private final Tabuleiro tabuleiro;
    private final String[] nomeJogadores = {"Branco", "Preto"};
    private final char[] jogadores = {'b','p'};
    private int[] pecasDisponiveis = {9, 9};
    private int[] pecasNoTabuleiro = {0, 0};
    private int jogadorAtual = 0;

    private final JButton[] botoesTabuleiro = new JButton[NUM_CASAS];

    private static final int GRID_SIZE = 7;
    private boolean faseInicial = true;
    private boolean modoCaptura = false;
    private int origemSelecionada = -1;

    private JFrame frame;
    private JLabel infoJogador;
    
    


    public ControleJogoGUI() {
        this.tabuleiro = new Tabuleiro();
        inicializarInterface();
    }

    class CustomPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
    
            // Enable anti-aliasing for smooth lines
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
            // Set line color
            g2d.setColor(Color.BLACK);
    
            // Set line thickness (4 times thicker, e.g., 8 pixels if the default is ~2)
            g2d.setStroke(new BasicStroke(8));
    
            // Outer square (perfect as requested)
            int outerMargin = 40; // Margin for the outer square
            g2d.drawRect(outerMargin, outerMargin, getWidth() - 2 * outerMargin, getHeight() - 2 * outerMargin);
    
            // Frame dimensions
            int frameWidth = getWidth();
            int frameHeight = getHeight();
    
            // Calculate even spacing
            int totalMargin = 160; // Total space between the outer and innermost square
            int spacing = totalMargin / 3; // Equal spacing between squares
    
            // First inner square (spaced evenly inside the outer square)
            int margin1 = outerMargin + spacing;
            int innerSquare1Size = Math.min(frameWidth, frameHeight) - 2 * margin1;
            int innerSquare1X = (frameWidth - innerSquare1Size) / 2;
            int innerSquare1Y = (frameHeight - innerSquare1Size) / 2;
            g2d.drawRect(innerSquare1X, innerSquare1Y, innerSquare1Size, innerSquare1Size);
    
            // Second inner square (spaced evenly inside the first inner square)
            int margin2 = outerMargin + 2 * spacing;
            int innerSquare2Size = Math.min(frameWidth, frameHeight) - 2 * margin2;
            int innerSquare2X = (frameWidth - innerSquare2Size) / 2;
            int innerSquare2Y = (frameHeight - innerSquare2Size) / 2;
            g2d.drawRect(innerSquare2X, innerSquare2Y, innerSquare2Size, innerSquare2Size);
    
            // Additional 4 lines
            int gap = 10; // Small gap between lines and the inner square
    
            // Vertical lines
            int verticalX = frameWidth / 2; // Centered in the frame
            g2d.drawLine(verticalX, outerMargin, verticalX, innerSquare2Y - gap); // Top segment
            g2d.drawLine(verticalX, innerSquare2Y + innerSquare2Size + gap, verticalX, frameHeight - outerMargin); // Bottom segment
    
            // Horizontal lines
            int horizontalY = frameHeight / 2; // Centered in the frame
            g2d.drawLine(outerMargin, horizontalY, innerSquare2X - gap, horizontalY); // Left segment
            g2d.drawLine(innerSquare2X + innerSquare2Size + gap, horizontalY, frameWidth - outerMargin, horizontalY); // Right segment
        }
    }
    
    
    






    public void inicializarInterface() {
        frame = new JFrame("Trilha");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500); 

        

        JPanel painelTabuleiro = new CustomPanel();
        painelTabuleiro.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE)); // GridLayout for buttons
        painelTabuleiro.setBackground(Color.DARK_GRAY.brighter());


        int[][] mapaTabuleiro = {
                {0, -1, -1, 1, -1, -1, 2},
                {-1, 8, -1, 9, -1, 10, -1},
                {-1, -1, 16, 17, 18, -1, -1},
                {7, 15, 23, -1, 19, 11, 3},
                {-1, -1, 22, 21, 20, -1, -1},
                {-1, 14, -1, 13, -1, 12, -1},
                {6, -1, -1, 5, -1, -1, 4}
        };

        for (int linha = 0; linha < GRID_SIZE; linha++) {
            for (int coluna = 0; coluna < GRID_SIZE; coluna++) {
                int indiceLogico = mapaTabuleiro[linha][coluna];
                if (indiceLogico >= 0) {
                    //cria botao para a posição
                    JButton botao = new BotaoCircular();
                    botao.setPreferredSize(new Dimension(50, 50));
                    botao.setBackground(Color.LIGHT_GRAY);
                    botao.setActionCommand(String.valueOf(indiceLogico));
                    botao.addActionListener(e -> tratarClique(Integer.parseInt(e.getActionCommand())));
                    botoesTabuleiro[indiceLogico] = botao;
                    painelTabuleiro.add(botao);

                }
                else {
                    painelTabuleiro.add(new JLabel());
                }
            }
        }

        infoJogador = new JLabel("Jogador atual: " + nomeJogadores[jogadorAtual] + ". Peças: " + pecasDisponiveis[jogadorAtual]);
        infoJogador.setHorizontalAlignment(SwingConstants.CENTER);
        infoJogador.setPreferredSize(new Dimension(600, 30));


        frame.add(infoJogador, BorderLayout.SOUTH);
        frame.add(painelTabuleiro, BorderLayout.CENTER);
        frame.setVisible(true);

    }


    

    private void atualizarInterface() {
        for (int i = 0; i < Tabuleiro.NUM_CASAS; i++) {
            char ocupante = tabuleiro.getCasa(i).getOcupante();
            if (ocupante == '-') {
                botoesTabuleiro[i].setText("");
                botoesTabuleiro[i].setBackground(Color.LIGHT_GRAY);
            } else if (ocupante == 'b') {
                botoesTabuleiro[i].setText("B");
                botoesTabuleiro[i].setBackground(Color.WHITE);
            } else if (ocupante == 'p') {
                botoesTabuleiro[i].setText("P");
                botoesTabuleiro[i].setBackground(Color.BLACK);
            }
            botoesTabuleiro[i].setEnabled(!modoCaptura || ocupante == jogadores[1 - jogadorAtual]);
        }

        if (faseInicial) {
            infoJogador.setText("Jogador atual: " + nomeJogadores[jogadorAtual] + ". Peças: " + pecasDisponiveis[jogadorAtual]);
            frame.repaint();
        }
        else {
            infoJogador.setText("Jogador atual: " + nomeJogadores[jogadorAtual]);
            frame.repaint();
        }
    }

    private void tratarClique(int posicao) {
        if (faseInicial && !modoCaptura) {
            System.out.println("Modo captura: " + modoCaptura + ", Fase inicial: " + faseInicial);

            if (pecasDisponiveis[jogadorAtual] > 0  && tabuleiro.colocarPeca(posicao, jogadores[jogadorAtual])) {
                pecasDisponiveis[jogadorAtual]--;
                pecasNoTabuleiro[jogadorAtual]++;

                if (tabuleiro.verificarMoinho(posicao, jogadores[jogadorAtual])) {
                    JOptionPane.showMessageDialog(frame, "Moinho! Remova uma peça do adversário.");
                    modoCaptura = true;
                    atualizarInterface();
                    return;
                }
                alternarJogador();
            }

            else {
                JOptionPane.showMessageDialog(frame, "Casa ocupada ou inválida. Tente novamente.");
            }
            System.out.println("peças do Branco: " + pecasDisponiveis[0] + ". Pecas do Preto: " + pecasDisponiveis[1]);
            if (pecasDisponiveis[0] == 0 && pecasDisponiveis[1] == 0) {
                faseInicial = false;
                modoCaptura = false;
                origemSelecionada = -1;
                //if (verificarVitoria()) reiniciarJogo();
                JOptionPane.showMessageDialog(frame, "Fase de movimentação iniciada!");
            }
        }

        else if (modoCaptura) {
            System.out.println("Modo captura: " + modoCaptura + ", Fase inicial: " + faseInicial);

            if (tabuleiro.removerPeca(posicao, jogadores[1 - jogadorAtual])) {
                pecasNoTabuleiro[1 - jogadorAtual]--;
                modoCaptura = tabuleiro.verificarBloqueio(jogadores[jogadorAtual]);
                if (pecasDisponiveis[0] == 0 && pecasDisponiveis[1] == 0 && verificarVitoria()) reiniciarJogo();

                alternarJogador();
            }

            else {
                JOptionPane.showMessageDialog(frame, "Escolha uma peça válida do adversário.");
            }
        }

        else {
            System.out.println("Modo captura: " + modoCaptura + ", Fase inicial: " + faseInicial);

            if (origemSelecionada == -1) {
                if (tabuleiro.getCasa(posicao).getOcupante() == jogadores[jogadorAtual]) {
                    origemSelecionada = posicao;
                    botoesTabuleiro[posicao].setBackground(Color.YELLOW);
                }

                else {
                    JOptionPane.showMessageDialog(frame, "Selecione uma de suas peças.");
                }
            }

            else {

                if (tabuleiro.moverPeca(origemSelecionada, posicao, jogadores[jogadorAtual])) {
                    origemSelecionada = -1;

                    if (tabuleiro.verificarMoinho(posicao, jogadores[jogadorAtual])) {
                        JOptionPane.showMessageDialog(frame, "Moinho! Remova uma peça do adversário!");
                        modoCaptura = true;
                        atualizarInterface();
                        return;
                    }

                    alternarJogador();
                }

                else {
                    JOptionPane.showMessageDialog(frame, "Movimento inválido. Tente novamente.");
                    origemSelecionada = -1;
                }
            }
        }
        atualizarInterface();
    }

    public void alternarJogador() {
        jogadorAtual = 1 - jogadorAtual;
    }

    public boolean verificarVitoria() {
        if (pecasNoTabuleiro[1 - jogadorAtual] < 3) {
            JOptionPane.showMessageDialog(frame, "Jogador " + nomeJogadores[jogadorAtual] + " venceu. O adversário tem menos de 3 peças!");
            return true;
        }

        if (tabuleiro.verificarBloqueio(jogadores[1 - jogadorAtual])) {
            JOptionPane.showMessageDialog(frame, "Jogador " + nomeJogadores[jogadorAtual] + " venceu por bloquear o adversário.");
            return true;
        }

        return false;
    }

    public void reiniciarJogo(){
        pecasDisponiveis = new int[]{9, 9};
        pecasNoTabuleiro = new int[]{0, 0};
        jogadorAtual = 0;
        faseInicial = true;
        modoCaptura = false;
        origemSelecionada = -1;

        tabuleiro.resetarTabuleiro();

        atualizarInterface();
        JOptionPane.showMessageDialog(frame, "Jogo reiniciado! Comece uma nova partida.");
    }



}
