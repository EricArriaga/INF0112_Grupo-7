import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Checkbox extends JFrame {

    private JCheckBox checkBox;
    private JButton continuarButton;
    private boolean valorBooleano;

    public Checkbox() {
        super("Exemplo Checkbox e Continuar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        checkBox = new JCheckBox("Ativar recurso");
        continuarButton = new JButton("Continuar");

        continuarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valorBooleano = checkBox.isSelected(); // Obtém o valor do checkbox
                dispose(); // Fecha a janela
                continuarExecucao(); // Chama o método para continuar a execução
            }
        });

        add(checkBox);
        add(continuarButton);

        pack();
        setLocationRelativeTo(null); // Centraliza a janela
        setVisible(true);
    }

    private void continuarExecucao() {
        if (valorBooleano) {
            System.out.println("Recurso ATIVADO!");
            // Coloque aqui o código para executar quando o checkbox estiver marcado
        } else {
            System.out.println("Recurso DESATIVADO!");
            // Coloque aqui o código para executar quando o checkbox estiver desmarcado
        }
        //O resto do código do seu programa continua aqui
        System.out.println("O resto do programa executa normalmente");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Checkbox());
    }
}