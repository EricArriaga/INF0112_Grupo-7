import java.util.ArrayList;
import java.util.List;

public class Casa {
    private int id;
    private char ocupante;
    private List<Casa> adjacentes;

    public Casa(int id) {
        this.id = id;
        this.ocupante = '-';
        this.adjacentes = new ArrayList<Casa>();
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

    public void adicionarAdjacente(Casa casa) {
        this.adjacentes.add(casa);
    }

}
