import java.util.Scanner;

public class ControleJogo {
    public static final int NUM_CASAS = 24;
    private final Tabuleiro tabuleiro;
    private final char[] jogadores = {'b', 'p'}; // Jogadores branco e preto
    private final int[] pecasDisponiveis = {9, 9}; // Cada jogador começa com 9 peças
    private int[] pecasNoTabuleiro = {0, 0};
    private int jogadorAtual = 0; // Índice do jogador atual: 0 para 'b', 1 para 'p'
    private int jogadorProximo = 1;
    private final Scanner scanner = new Scanner(System.in);

    public ControleJogo() {
        this.tabuleiro = new Tabuleiro();
    }

    public void iniciarJogo() {
        System.out.println("Bem-vindo ao jogo Trilha!");
        tabuleiro.imprimirTabuleiro();

        //fase de posicionamento inicial
        while (pecasDisponiveis[0] > 0 || pecasDisponiveis[1] > 0) {
            System.out.println("Jogador " + jogadores[jogadorAtual] + ", é sua vez de colocar uma peça.");
            System.out.println("Peças disponíveis: " + pecasDisponiveis[jogadorAtual]);

            int posicao = solicitarPosicao();
            boolean sucesso = tabuleiro.colocarPeca(posicao, jogadores[jogadorAtual]);

            if (sucesso) {
                pecasDisponiveis[jogadorAtual]--;
                pecasNoTabuleiro[jogadorAtual]++;
                tabuleiro.imprimirTabuleiro();

                if (tabuleiro.verificarMoinho(posicao, jogadores[jogadorAtual])) {
                    System.out.println("Moinho formado! Jogador " + jogadores[jogadorAtual] + ", remova uma peça do adversário.");
                    capturarPeca();
                    if (verificarVitoria()){
                        return;
                    }
                    if (tabuleiro.verificarBloqueio(jogadores[1 - jogadorAtual])) {
                        return;
                    }

                }



                alternarJogador();
            } else {
                System.out.println("Posição inválida ou já ocupada. Tente novamente.");
            }
        }

        System.out.println("Fase de movimentação iniciada!");
        // Fase de movimentação (lógica simplificada para demonstração)
        while (true) {
            System.out.println("Jogador " + jogadores[jogadorAtual] + ", é sua vez de mover uma peça.");
            int origem = solicitarPosicao("Digite a posição da peça que deseja mover: ");
            int destino = solicitarPosicao("Digite a posição para onde deseja mover: ");

            boolean sucesso = tabuleiro.moverPeca(origem, destino, jogadores[jogadorAtual]);
            if (sucesso) {
                tabuleiro.imprimirTabuleiro();

                if (tabuleiro.verificarMoinho(destino, jogadores[jogadorAtual])) {
                    System.out.println("Moinho formado! Jogador " + jogadores[jogadorAtual] + ", remova uma peça do adversário.");
                    capturarPeca();
                    if (verificarVitoria()){
                        return;
                    }
                    if (tabuleiro.verificarBloqueio(jogadores[1 - jogadorAtual])) {
                        return;
                    }
                }



//                if (verificarBloqueio()){
//                    System.out.println("Jogador " + jogadores[jogadorAtual] + " não tem mais movimentos válidos! Você perdeu.");
//                    return;
//                }

                alternarJogador();
            } else {
                System.out.println("Movimento inválido. Tente novamente.");
            }

            // Condições de vitória ou empate podem ser verificadas aqui
        }
    }

    private int solicitarPosicao() {
        return solicitarPosicao("Digite a posição desejada (0-23): ");
    }

    private int solicitarPosicao(String mensagem) {
        System.out.print(mensagem);
        int posicao;
        while (true) {
            try {
                posicao = Integer.parseInt(scanner.nextLine());
                if (posicao >= 0 && posicao < 24) {
                    return posicao;
                } else {
                    System.out.print("Posição fora do tabuleiro. " + mensagem);
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. " + mensagem);
            }
        }
    }

    private void capturarPeca() {
        System.out.println("Escolha uma peça adversária para remover.");
        while (true) {
            int posicao = solicitarPosicao();
            boolean sucesso = tabuleiro.removerPeca(posicao, jogadores[1 - jogadorAtual]);
            if (sucesso) {
                System.out.println("Peça capturada com sucesso.");
                tabuleiro.imprimirTabuleiro();
                pecasNoTabuleiro[1 - jogadorAtual]--;
                break;
            } else {
                System.out.println("Posição inválida ou a peça escolhida não pertence ao adversário. Tente novamente.");
            }
        }
    }

    private void alternarJogador() {
        jogadorAtual = 1 - jogadorAtual; // Alterna entre 0 e 1
    }

    private boolean verificarVitoria() {
        int pecasAdversario = pecasNoTabuleiro[1 - jogadorAtual];
        if (pecasAdversario <= 2) {
            System.out.println("Jogador " + jogadores[jogadorAtual] + " venceu! O adversário tem apenas 2 peças restantes.");
            return true;
        }

        return false;
    }


}
