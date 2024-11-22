import java.util.List;

public class Casa {
    private int pos_x;
    private int pos_y;
    private int index;
    private String corOcupada;
    private List<Casa> vizinhos;

    public Casa(int x, int y, int index, String corOcupada) {
        this.pos_x = x;
        this.pos_y = y;
        this.index = index;
        this.corOcupada = corOcupada;
    }

    public Casa(int x, int y, int index) {
        this.pos_x = x;
        this.pos_y = y;
        this.index = index;
    }

    public void desocupaCasa() {
        this.corOcupada = "";
    }

    public void setCorOcupada(String corOcupada) {
        this.corOcupada = corOcupada;
    }

    public String getCorOcupada() {
        return this.corOcupada;
    }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;

    }
    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setVizinhos(List<Casa> vizinhos) {
        this.vizinhos = vizinhos;
    }

    public int getPos_x() {
        return this.pos_x;
    }

    public int getPos_y() {
        return this.pos_y;
    }

    public int getIndex() {
        return this.index;
    }

    public List<Casa> getVizinhos() {
        return this.vizinhos;
    }

}
