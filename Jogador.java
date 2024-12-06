public class Jogador {
    private String nome;
    private char simbolo;
    private int pecasRestantes;
    private int pecasNoTabuleiro;

    public Jogador(String nome, char simbolo) {
        this.nome = nome;
        this.simbolo = simbolo;
        this.pecasRestantes = 9;
        this.pecasNoTabuleiro = 0;
    }

    public String getNome() {
        return nome;
    }

    public char getSimbolo() {
        return simbolo;
    }

    public boolean temPecasRestantes() {
        return pecasRestantes > 0;
    }

    public void colocarPeca() {
        if (pecasRestantes > 0) {
            pecasRestantes--;
            pecasNoTabuleiro++;
        }
    }

    public void removerPeca() {
        pecasNoTabuleiro--;
    }

    public int getPecasNoTabuleiro() {
        return pecasNoTabuleiro;
    }

    public void printInfo(){
        System.out.println("Nome: " + nome);
        System.out.println("Simbolo: " + simbolo);
        System.out.println("Pecas Restantes: " + pecasRestantes);
        System.out.println("Pecas No Tabuleiro: " + pecasNoTabuleiro);

    }
}

