import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CasaTest {
    private Tabuleiro tabuleiro;
    private Casa casa;

    @BeforeEach
    void setUp() {
        casa = new Casa(0);
    }

    @Test
    @DisplayName("Casa vazia")
    void testCasaVazia() {
        assertEquals('-', casa.getOcupante(), "Deveria estar vazia: -");
    }

    @Test
    @DisplayName("Casa com peça preta")
    void testPecaPreta() {
        casa.setOcupante('p');
        assertEquals('p', casa.getOcupante(), "Peça deveria ser: p");
    }

    @Test
    @DisplayName("Casa com peça branca")
    void testCasaBranca() {
        casa.setOcupante('b');
        assertEquals('b', casa.getOcupante(), "Peça deveria ser: b");
    }

    @Test
    @DisplayName("Casa e seu vizinho")
    void testVizinhoBranca() {
        Casa cazinha = new Casa(1);
        cazinha.setOcupante('b');
        casa.adicionarAdjacente(cazinha);
        assertEquals('b', cazinha.getOcupante(), "Deveria estar vazia: B");
    }

    @Test
    @DisplayName("Lista de vizinhos")
    void testListaVizinhos() {
        Casa cazinha = new Casa(1);
        Casa cazinha2 = new Casa(2);

        List<Casa> vizinhos = List.of(cazinha, cazinha2);

        assertEquals(vizinhos.get(0).getOcupante(), cazinha.getOcupante(), "Deveria estar vazia: -");
    }

}
