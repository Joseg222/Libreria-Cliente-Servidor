package app.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class Servidor {
    private static final int PORT = 5555;

    public static void main(String[] args) {
        // Pool de hilos para manejar múltiples clientes
        ExecutorService pool = Executors.newFixedThreadPool(8);

        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Servidor escuchando en puerto " + PORT + " ...");

            while (true) {
                Socket client = server.accept();
                System.out.println("Cliente conectado: " + client.getRemoteSocketAddress());
                pool.submit(new ClientHandler(client)); // Manejo concurrente
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }
}