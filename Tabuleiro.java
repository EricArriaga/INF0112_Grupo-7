import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
    public final static int NUM_CASAS = 24;
    private final List<Casa> casas;
    private final int[][] gruposDeMoinho = {
            {0, 1, 2}, {8, 9, 10}, {16, 17, 18}, {7, 15, 23}, {3, 11, 19}, {20, 21, 22}, {12, 13, 14}, {4, 5, 6},
            {0, 7, 6}, {8, 15, 14}, {16, 23, 22}, {1, 9, 17}, {21, 13, 5}, {18, 19, 20}, {10, 11, 12}, {2, 3, 4}
    };

    public Tabuleiro() {
        casas = new ArrayList<Casa>();
        for (int i = 0; i < NUM_CASAS; i++){
            casas.add(new Casa(i));
        }
        conectarCasas();
    }

    private void conectarCasas() {
        //matriz com as conexões do tabuleiro
        //cada linha representa uma casa e contém os IDs das casas adjacentes
        int[][] conexoes = {
                // Quadrado externo
                {1, 7}, {0, 2, 9}, {1, 3}, {2, 4, 11}, {3, 5}, {4, 6, 13}, {5, 7}, {0, 6, 15},
                // Quadrado médio
                {9, 15}, {1, 8, 10, 17}, {9, 11}, {3, 10, 12, 19}, {11, 13}, {5, 12, 14, 21}, {13, 15}, {7, 8, 14, 23},
                // Quadrado interno
                {17, 23}, {9, 16, 18}, {17, 19}, {11, 18, 20}, {19, 21}, {13, 20, 22}, {21, 23}, {15, 16, 22}
        };

        //itera sobre a matriz e conecta as casas
        for (int i = 0; i < conexoes.length; i++) {
            Casa casaAtual = casas.get(i);
            for (int adjacente : conexoes[i]) {
                casaAtual.adicionarAdjacente(casas.get(adjacente));
            }
        }
    }

    public boolean colocarPeca(int id, char simbolo) {
        Casa casa = casas.get(id);

        if (casa.getOcupante() == '-') {
            casa.setOcupante(simbolo);
            return true;
        }
        return false;
    }

    public boolean moverPeca(int origem, int destino, char simbolo) {
        Casa casaOrigem = casas.get(origem);
        Casa casaDestino = casas.get(destino);

        if (casaOrigem.getOcupante() == simbolo && casaDestino.getOcupante() == '-'
        && casaOrigem.getAdjacentes().contains(casaDestino)) {
            casaOrigem.setOcupante('-');
            casaDestino.setOcupante(simbolo);
            return true;
        }

        return false;
    }

    public boolean removerPeca(int indice, char simbolo) {
        if (indice < 0 || indice >= casas.size()) {
            System.out.println("Erro ao remover peca: indice invalido");
            return false;
        }

        Casa casa = casas.get(indice);

        if (casa.getOcupante() == '-') {
            System.out.println("Erro ao remover peca: nada para remover");
            return false;
        }

        if (casa.getOcupante() == simbolo) {
            casa.setOcupante('-');
            System.out.println("Removido com sucesso");
            return true;
        }

        System.out.println("Erro ao remover peca: simbolo nao pode ser removido");
        return false;
    }

    public boolean verificarMoinho(int indice, char jogador){
        //verifica se os indices fazem sentido
        if (indice < 0 || indice >= casas.size()) {
            System.out.println("Erro ao verificar moinho: indice invalido");
            return false;
        }

        Casa casa = casas.get(indice);

        //verificar se a casa está ocupada pelo jogador
        if (casa.getOcupante() != jogador) {
            return false;
        }

        //itera pelas combinações de vizinhos da casa
        for (int[] grupo : gruposDeMoinho) {
            if (grupo[0] == indice || grupo[1] == indice || grupo[2] == indice){
                if (casas.get(grupo[0]).getOcupante() == jogador &&
                    casas.get(grupo[1]).getOcupante() == jogador &&
                    casas.get(grupo[2]).getOcupante() == jogador){
                        return true;
                }
            }
        }

        return false;
    }


    public void imprimirTabuleiro() {
        //array para coletar os símbolos das casas
        String[] simbolos = new String[24];
        for (int i = 0; i < NUM_CASAS; i++) {
            simbolos[i] = casas.get(i).getOcupante() == ' ' ? "-" : Character.toString(casas.get(i).getOcupante());
        }

        //imprimindo o tabuleiro no formato esperado
        System.out.println();
        System.out.println(simbolos[0] + " ________ " + simbolos[1] + " ________ " + simbolos[2]);
        System.out.println("| " + simbolos[8] + " ______ " + simbolos[9] + " ______ " + simbolos[10] + " |");
        System.out.println("| | " + simbolos[16] + " ____ " + simbolos[17] + " ____ " + simbolos[18] + " | |");
        System.out.println(simbolos[7] + " " + simbolos[15] + " " + simbolos[23] + " ___________ " + simbolos[19] + " " + simbolos[11] + " " + simbolos[3]);
        System.out.println("| | " + simbolos[22] + " ____ " + simbolos[21] + " ____ " + simbolos[20] + " | |");
        System.out.println("| " + simbolos[14] + " ______ " + simbolos[13] + " ______ " + simbolos[12] + " |");
        System.out.println(simbolos[6] + " ________ " + simbolos[5] + " ________ " + simbolos[4]);
        System.out.println();
    }

    public boolean verificarBloqueio(char jogador){
        for (int i = 0; i < NUM_CASAS; i++) {
            if (casas.get(i).getOcupante() == jogador) {
                for (Casa adjacente : casas.get(i).getAdjacentes()) {
                    if (adjacente.getOcupante() == '-') {
                        return false;   //encontrou uma posição vazia para se mover
                    }
                }
            }
        }
        System.out.println("Bloqueio com sucesso. O jogador " + jogador + " perdeu!");
        return true;    //não há como se mover = bloqueio!
    }

    public char getPeca(int pos) {
        return casas.get(pos).getOcupante();
    }

    public Casa getCasa(int posicao) {
        return casas.get(posicao);
    }

    public void resetarTabuleiro() {
        for (int i = 0; i < NUM_CASAS; i++) {
            casas.get(i).setOcupante('-');
        }
    }
}