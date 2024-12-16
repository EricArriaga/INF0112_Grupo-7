import javax.swing.*;

public class Main extends ControleJogoGUI {
    public static void main(String[] args) {
        // inicia interface gráfica e o jogo
        SwingUtilities.invokeLater(ControleJogoGUI::new);
    }
}
