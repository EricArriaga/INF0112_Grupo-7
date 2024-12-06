import java.util.ArrayList;
import java.util.List;

public class Casa {
    private int id;
    private char ocupante;
    private List<Casa> adjacentes;

    public Casa(int id, char ocupante, List<Casa> adjacentes) {
        this.id = id;
        this.ocupante = ocupante;
        this.adjacentes = adjacentes;
    }

    public Casa(int id) {
        this.id = id;
        this.ocupante = '-';
        this.adjacentes = new ArrayList<Casa>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getOcupante() {
        return ocupante;
    }

    public void setOcupante(char ocupante) {
        this.ocupante = ocupante;
    }

    public List<Casa> getAdjacentes() {
        return adjacentes;
    }

    public void setAdjacentes(List<Casa> adjacentes) {
        this.adjacentes = adjacentes;
    }

    public void adicionarAdjacente(Casa casa) {
        this.adjacentes.add(casa);
    }

    public void printInfo(){
        System.out.println("ID: " + this.id);
        System.out.println("Ocupante: " + this.ocupante);
        System.out.println("Adjacentes: " + this.adjacentes);
    }
}
