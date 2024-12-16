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

    

    public void inicializarInterface() {
        frame = new JFrame("Trilha");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(520, 620);
        frame.setResizable(false);
        

        JPanel painelTabuleiro = new CustomPanel();
        painelTabuleiro.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE)); // GridLayout for buttons
        painelTabuleiro.setBackground(new Color(251, 251, 204));


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
                    botao.setBackground(new Color(132, 132, 112));
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
                botoesTabuleiro[i].setBackground(new Color(132, 132, 112));
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
