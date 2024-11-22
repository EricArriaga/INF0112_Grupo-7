public class Jogador {
    private boolean seuTurno;
    private String suaCor;
    private int pecasDisponiveis;
    private int pecasNoTabuleiro;


    public Jogador() {}

    public void decPecasDisponiveis(){
        this.pecasDisponiveis--;
    }

    public void decPecasNoTabuleiro(){
        this.pecasNoTabuleiro--;
    }

    public void incPecasDisponiveis(){
        this.pecasDisponiveis++;
    }

    public void incPecasNoTabuleiro(){
        this.pecasNoTabuleiro++;
    }


}
