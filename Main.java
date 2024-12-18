import javax.swing.*;

public class Main extends ControleJogoGUI {
    public static void main(String[] args) {
        
        Cliente cliente = Cliente.coletar_dados_servidor();

        // inicia interface gráfica e o jogo
        SwingUtilities.invokeLater(() -> new ControleJogoGUI(cliente));
    }
}
