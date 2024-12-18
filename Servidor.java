import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Servidor {
    private static List<PrintWriter> clientes = new ArrayList<>();

    public static void main(String[] args) {

        int porta;

        System.out.println("Insira a porta:");

        try (Scanner sc = new Scanner(System.in)) {
            porta = sc.nextInt();
        } catch (Exception e) {
            porta = 12345;
        }


        System.out.println("Servidor iniciado...");

        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            while (true) {
                Socket cliente = serverSocket.accept();
                System.out.println("Novo cliente conectado: " + cliente.getInetAddress());

                // Adiciona o cliente à lista
                PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
                clientes.add(out);

                // Cria uma thread para lidar com o cliente
                new Thread(new ClienteHandler(cliente)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClienteHandler implements Runnable {
        private Socket socket;

        public ClienteHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String mensagem;
                while ((mensagem = in.readLine()) != null) {
                    System.out.println("Mensagem recebida: " + mensagem);
                    // Envia a mensagem para todos os clientes
                    for (PrintWriter cliente : clientes) {
                        cliente.println(mensagem);
                    }
                }
            } catch (IOException e) {
                System.out.println("Cliente desconectado.");
            }
        }
    }
}
