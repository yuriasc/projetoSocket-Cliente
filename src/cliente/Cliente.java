package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {

    public static void main(String[] args) {
        try {
            final Socket cliente = new Socket("127.0.0.1", 9090);
            // lendo mensagem do servidor
            new Thread() {
                public void run() {
                    try {
                        BufferedReader leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

                        while (true) {
                            String mensagem = leitor.readLine();
                            System.out.println(mensagem);
                        }

                    } catch (IOException e) {
                        System.out.println("Impossivel ler a mensagem do servidor");
                        e.printStackTrace();
                    }

                }
            }.start();
            // escrevendo mensagem para o servidor
            PrintWriter escritor = new PrintWriter(cliente.getOutputStream(), true);
            BufferedReader leitorTerminal = new BufferedReader(new InputStreamReader(System.in));
            
            String mensagemTerminal = "";
            
            while (true) {
                mensagemTerminal = leitorTerminal.readLine();
                if (mensagemTerminal == null || mensagemTerminal.length() == 0) {
                    System.exit(0);
                }
                escritor.println(mensagemTerminal);
                if (mensagemTerminal.equalsIgnoreCase("bye")) {
                    System.exit(0);
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("O cliente fechou a conexão");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
