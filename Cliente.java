import javax.swing.*;
import java.io.*;
import java.net.Socket;
public class Cliente {

    private boolean online;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private ControleJogoGUI controle;

    public static final String IP_PADRAO = "localhost";
    public static final int PORTA_PADRAO = 12345;
    

    public Cliente(String enderecoServidor, int porta, boolean online) {

        this.online = online;

        if (online) {
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
    }

    public static Cliente coletar_dados_servidor() {
        String enderecoServidor = JOptionPane.showInputDialog("Digite o endereço do servidor:", IP_PADRAO);
        if (enderecoServidor == null || enderecoServidor.isEmpty()){
            enderecoServidor = IP_PADRAO;
        }
        String portaStr = JOptionPane.showInputDialog("Digite a porta do servidor:", PORTA_PADRAO);
        int porta = PORTA_PADRAO;
                try {
                    porta = Integer.parseInt(portaStr);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Porta inválida. Usando porta padrão 12345", "Erro", JOptionPane.ERROR_MESSAGE);
                }
        final String enderecoServidorFinal = enderecoServidor;
        final int portaFinal = porta;

        return new Cliente(enderecoServidorFinal, portaFinal, true);
    }

    public static Cliente iniciar_offline(){    
        return new Cliente(IP_PADRAO, PORTA_PADRAO, false);
    }

    public void tratarClique(int int1) {
        if (online) {
            if (out != null) {
                out.println(int1);
            } else {
                SwingUtilities.invokeLater(() -> 
                JOptionPane.showMessageDialog(null, "Não conectado ao servidor."));
            }
        } else {
            controle.tratarClique(int1);
        }
    }

    public void setControle(ControleJogoGUI controle){
        this.controle = controle;
    }

}