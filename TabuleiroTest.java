import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TabuleiroTest {

    @Test
    void colocarPeca() {
        int ID = 1;
        Tabuleiro tabuleiro = new Tabuleiro();
        Casa casa = new Casa(ID);

        tabuleiro.colocarPeca(ID, 'X');
        assertEquals('X', tabuleiro.getPeca(1), "A peça foi colocada com sucesso.");
    }

    @Test
    void moverPeca() {
        Tabuleiro tabuleiro = new Tabuleiro();
        Casa origem = new Casa(1);
        Casa destino = new Casa(2);

        tabuleiro.colocarPeca(origem.getId(), 'X');
        tabuleiro.moverPeca(origem.getId(), destino.getId(), 'X');

        assertEquals('X', tabuleiro.getPeca(destino.getId()), "A peça não foi movida corretamente para o destino.");
        assertEquals('-', tabuleiro.getPeca(origem.getId()), "A peça não foi removida corretamente da casa de origem.");
    }

    @Test
    void removerPeca() {
        Tabuleiro tabuleiro = new Tabuleiro();
        Casa origem = new Casa(1);

        tabuleiro.colocarPeca(origem.getId(), 'X');
        tabuleiro.removerPeca(origem.getId(), 'X');

        assertEquals('-', tabuleiro.getPeca(origem.getId()), "A peça não foi removida corretamente da casa.");
    }

    @Test
    void verificarMoinho() {
        Tabuleiro tabuleiro = new Tabuleiro();
        Casa casa0 = new Casa(0);
        Casa casa1 = new Casa(1);
        Casa casa2 = new Casa(2);

        tabuleiro.colocarPeca(casa0.getId(), 'X');
        tabuleiro.colocarPeca(casa1.getId(), 'X');
        tabuleiro.colocarPeca(casa2.getId(), 'X');

        assertTrue(tabuleiro.verificarMoinho(casa0.getId(), 'X'), "Deveria ser reconhecido o moinho.");

    }

    @Test
    void verificarBloqueio() {
        Tabuleiro tabuleiro = new Tabuleiro();
        Casa casa0 = new Casa(0);
        Casa casa1 = new Casa(1);
        Casa casa2 = new Casa(2);
        Casa casa9 = new Casa(9);

        tabuleiro.colocarPeca(casa0.getId(), 'X');
        tabuleiro.colocarPeca(casa1.getId(), 'Y');
        tabuleiro.colocarPeca(casa2.getId(), 'X');
        tabuleiro.colocarPeca(casa9.getId(), 'X');

        assertTrue(tabuleiro.verificarBloqueio('Y'), "Deveria ser reconhecido o moinho.");
    }

    @Test
    void getPeca() {
        Tabuleiro tabuleiro = new Tabuleiro();
        Casa casa0 = new Casa(0);

        tabuleiro.colocarPeca(casa0.getId(), 'X');
        assertEquals('X', tabuleiro.getPeca(casa0.getId()), "A peça na casa não foi retornada corretamente.");
    }

    @Test
    void getCasa() {
        Tabuleiro tabuleiro = new Tabuleiro();
        Casa casa0 = new Casa(0);

        tabuleiro.colocarPeca(casa0.getId(), 'X');
        assertEquals(casa0.getId(), tabuleiro.getCasa(0).getId(), "A casa com o ID não foi retornada corretamente." );
    }

    @Test
    void resetarTabuleiro() {
        Tabuleiro tabuleiro = new Tabuleiro();
        Casa casa0 = new Casa(0);

        tabuleiro.colocarPeca(casa0.getId(), 'X');
        tabuleiro.resetarTabuleiro();

        assertEquals('-', tabuleiro.getCasa(0).getOcupante(), "O tabuleiro não foi resetado corretamente.");
    }
}