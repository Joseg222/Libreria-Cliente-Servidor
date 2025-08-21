package app.net;

import app.model.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    // Lista de publicaciones compartida entre todos los clientes
    private static final List<Publicacion> publicaciones = new ArrayList<>();

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            // Crear streams para enviar/recibir objetos
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());

            Object obj;
            while ((obj = in.readObject()) != null) {
                if (!(obj instanceof String)) continue;
                String comando = (String) obj;

                switch (comando) {
                    case "LIST":
                        // Enviar copia de la lista de publicaciones
                        out.writeObject(new ArrayList<>(publicaciones));
                        out.flush();
                        break;

                    case "ADD":
                        // Recibir objeto Publicacion y agregarlo
                        Publicacion p = (Publicacion) in.readObject();
                        publicaciones.add(p);
                        out.writeObject("OK"); // Confirmación al cliente
                        out.flush();
                        break;

                    case "SEARCH":
                        // Recibir título a buscar y devolver coincidencias
                        String titulo = (String) in.readObject();
                        List<Publicacion> resultados = new ArrayList<>();
                        for (Publicacion pub : publicaciones) {
                            if (pub.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                                resultados.add(pub);
                            }
                        }
                        out.writeObject(resultados);
                        out.flush();
                        break;

                    case "EXIT":
                        out.writeObject("BYE"); // Confirmación para cerrar cliente
                        out.flush();
                        return; // Salir del hilo

                    default:
                        // Comando desconocido
                        out.writeObject("ERROR: Comando no reconocido");
                        out.flush();
                        break;
                }
            }

        } catch (Exception e) {
            System.out.println("Cliente desconectado: " + client.getRemoteSocketAddress());
        } finally {
            try { client.close(); } catch (IOException ignored) {}
        }
    }
}