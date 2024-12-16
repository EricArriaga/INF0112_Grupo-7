import javax.swing.*;
import java.awt.*;


public class BotaoCircular extends JButton {


    public BotaoCircular() {
        super();
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false); // Opcional, evita bordas indesejadas
    }



    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.yellow); // Cor ao clicar
        } else {
            g.setColor(getBackground());
        }

        int padding = 10;
        g.fillOval(padding, padding, getWidth() - 2 * padding, getWidth() - 2 * padding); // Preenche a área circular


        super.paintComponent(g);
    }

    /*
    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawOval(0, 0, getWidth() - 1, getHeight() - 1); // Desenha a borda circular
    }
    */


    @Override
    public boolean contains(int x, int y) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY);
        return Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2) <= Math.pow(radius, 2);
    }

    @Override
    public Dimension getPreferredSize() {
        // Garante que o botão tenha a mesma largura e altura
        int size = Math.max(getWidth(), getHeight());
        return new Dimension(size, size);
    }
}
