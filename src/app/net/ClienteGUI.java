package app.net;

import app.model.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.List;
import javax.swing.*;

public class ClienteGUI extends JFrame {
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;

    public ClienteGUI() {
        setTitle("Cliente Librería");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana

        // Panel principal
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        // Botones
        JButton btnListar = new JButton("Listar publicaciones");
        JButton btnAgregarLibro = new JButton("Agregar Libro");
        JButton btnAgregarRevista = new JButton("Agregar Revista");
        JButton btnAgregarEBook = new JButton("Agregar EBook");
        JButton btnBuscar = new JButton("Buscar por título");
        JButton btnSalir = new JButton("Salir");

        Font btnFont = new Font("Segoe UI", Font.PLAIN, 14);
        for (JButton b : new JButton[]{btnListar, btnAgregarLibro, btnAgregarRevista, btnAgregarEBook, btnBuscar, btnSalir}) {
            b.setFont(btnFont);
            b.setBackground(new Color(70, 130, 180));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
        }

        // Colocación de botones
        c.gridx = 0; c.gridy = 0; panel.add(btnListar, c);
        c.gridx = 1; c.gridy = 0; panel.add(btnAgregarLibro, c);
        c.gridx = 0; c.gridy = 1; panel.add(btnAgregarRevista, c);
        c.gridx = 1; c.gridy = 1; panel.add(btnAgregarEBook, c);
        c.gridx = 0; c.gridy = 2; panel.add(btnBuscar, c);
        c.gridx = 1; c.gridy = 2; panel.add(btnSalir, c);

        add(panel);

        // Conexión al servidor
        try {
            socket = new Socket("127.0.0.1", 5555);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No se pudo conectar al servidor.");
            System.exit(1);
        }

        // Listeners
        btnListar.addActionListener(e -> listarPublicaciones());
        btnAgregarLibro.addActionListener(e -> agregarLibro());
        btnAgregarRevista.addActionListener(e -> agregarRevista());
        btnAgregarEBook.addActionListener(e -> agregarEBook());
        btnBuscar.addActionListener(e -> buscarPublicacion());
        btnSalir.addActionListener(e -> salir());
    }

    private void salir() {
        try {
            out.writeObject("EXIT");
            out.flush();
            in.readObject();
        } catch (Exception ignored) {}
        finally {
            try { socket.close(); } catch (IOException ignored) {}
            dispose();
            System.exit(0);
        }
    }

    // ---------------------------
    // Métodos de funcionalidad
    // ---------------------------
    private void listarPublicaciones() {
        try {
            out.writeObject("LIST");
            out.flush();
            List<Publicacion> list = (List<Publicacion>) in.readObject();
            mostrarLista(list, "Publicaciones");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void agregarLibro() {
        try {
            String titulo = JOptionPane.showInputDialog(this, "Título:");
            String autor = JOptionPane.showInputDialog(this, "Autor:");
            int anio = Integer.parseInt(JOptionPane.showInputDialog(this, "Año:"));
            String isbn = JOptionPane.showInputDialog(this, "ISBN:");
            int paginas = Integer.parseInt(JOptionPane.showInputDialog(this, "Número de páginas:"));

            Libro l = new Libro(null, titulo, autor, anio, isbn, paginas);

            out.writeObject("ADD");
            out.writeObject(l);
            out.flush();
            String ok = (String) in.readObject();
            if ("OK".equals(ok)) JOptionPane.showMessageDialog(this, "Libro agregado correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void agregarRevista() {
        try {
            String titulo = JOptionPane.showInputDialog(this, "Título:");
            String autor = JOptionPane.showInputDialog(this, "Autor:");
            int anio = Integer.parseInt(JOptionPane.showInputDialog(this, "Año:"));
            int numero = Integer.parseInt(JOptionPane.showInputDialog(this, "Número de revista:"));

            Revista r = new Revista(null, titulo, autor, anio, numero);

            out.writeObject("ADD");
            out.writeObject(r);
            out.flush();
            String ok = (String) in.readObject();
            if ("OK".equals(ok)) JOptionPane.showMessageDialog(this, "Revista agregada correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void agregarEBook() {
        try {
            String titulo = JOptionPane.showInputDialog(this, "Título:");
            String autor = JOptionPane.showInputDialog(this, "Autor:");
            int anio = Integer.parseInt(JOptionPane.showInputDialog(this, "Año:"));
            String formato = JOptionPane.showInputDialog(this, "Formato:");
            double tamano = Double.parseDouble(JOptionPane.showInputDialog(this, "Tamaño MB:"));

            EBook e = new EBook(null, titulo, autor, anio, formato, tamano);

            out.writeObject("ADD");
            out.writeObject(e);
            out.flush();
            String ok = (String) in.readObject();
            if ("OK".equals(ok)) JOptionPane.showMessageDialog(this, "EBook agregado correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void buscarPublicacion() {
        try {
            String q = JOptionPane.showInputDialog(this, "Buscar título:");
            out.writeObject("SEARCH");
            out.writeObject(q);
            out.flush();
            List<Publicacion> results = (List<Publicacion>) in.readObject();
            mostrarLista(results, "Resultados de búsqueda");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void mostrarLista(List<Publicacion> list, String titulo) {
        if (list == null || list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay publicaciones.", titulo, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Publicacion p : list) sb.append(p).append("\n");
        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(450, 250));
        JOptionPane.showMessageDialog(this, scroll, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClienteGUI().setVisible(true));
    }
}