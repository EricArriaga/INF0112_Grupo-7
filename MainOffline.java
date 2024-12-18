import javax.swing.*;

public class MainOffline extends ControleJogoGUI {
    public static void main(String[] args) {
        
        Cliente cliente = Cliente.iniciar_offline();

        // inicia interface gráfica e o jogo
        SwingUtilities.invokeLater(() -> new ControleJogoGUI(cliente));
    }
}
