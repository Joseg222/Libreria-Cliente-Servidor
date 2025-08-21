package app;

import app.db.DerbyManager;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        System.out.println("ServidorFideflix iniciado.");

        // Inicializa Derby
        try (Connection conn = DerbyManager.get()) {
            System.out.println("Base de datos lista y conexión establecida correctamente.");
        } catch (Exception e) {
            System.err.println("Error al inicializar la base de datos:");
            e.printStackTrace();
        }

        System.out.println("Para probar la aplicación, ejecuta Servidor o Cliente desde VS Code.");
    }
}
