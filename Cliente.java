import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Cliente {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private ControleJogoGUI controle;
    

    public Cliente(String enderecoServidor, int porta) {
        new Thread(() -> {
            try {
                socket = new Socket(enderecoServidor, porta);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String mensagemServidor;
                while ((mensagemServidor = in.readLine()) != null) {
                    final int casaJogadaNum = Integer.parseInt(mensagemServidor);
                    SwingUtilities.invokeLater(() -> controle.tratarClique(casaJogadaNum));
                }
            } catch (IOException e) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Erro de conexão: " + e.getMessage()));
            } finally {
                try {
                    if (socket != null) socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static Cliente coletar_dados_servidor() {
        String enderecoServidor = JOptionPane.showInputDialog("Digite o endereço do servidor:", "localhost");
        if (enderecoServidor == null || enderecoServidor.isEmpty()){
            enderecoServidor = "localhost";
        }
        String portaStr = JOptionPane.showInputDialog("Digite a porta do servidor:", "12345");
        int porta = 12345;
                try {
                    porta = Integer.parseInt(portaStr);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Porta inválida. Usando porta padrão 12345", "Erro", JOptionPane.ERROR_MESSAGE);
                }
        final String enderecoServidorFinal = enderecoServidor;
        final int portaFinal = porta;
        return new Cliente(enderecoServidorFinal, portaFinal);
    }

    public void tratarClique(int int1) {
        if (out != null) {
            out.println(int1);
        } else {
            SwingUtilities.invokeLater(() -> 
            JOptionPane.showMessageDialog(null, "Não conectado ao servidor."));
        }
    }

    public void setControle(ControleJogoGUI controle){
        this.controle = controle;
    }
}