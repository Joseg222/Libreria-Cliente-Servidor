package app.service;

import app.db.DerbyManager;
import app.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Libreria {

    public Publicacion agregar(Publicacion p) throws SQLException {
        String sql = "INSERT INTO PUBLICACION (TIPO,TITULO,AUTOR,ANIO,ISBN,PAGINAS,NUMERO,FORMATO,TAMANOMB) " +
                     "VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection c = DerbyManager.get();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getTipo());
            ps.setString(2, p.getTitulo());
            ps.setString(3, p.getAutor());
            ps.setInt(4, p.getAnio());

            if (p instanceof Libro l) {
                ps.setString(5, l.getIsbn());
                ps.setObject(6, l.getPaginas(), Types.INTEGER);
                ps.setNull(7, Types.INTEGER);
                ps.setNull(8, Types.VARCHAR);
                ps.setNull(9, Types.DOUBLE);
            } else if (p instanceof Revista r) {
                ps.setNull(5, Types.VARCHAR);
                ps.setNull(6, Types.INTEGER);
                ps.setObject(7, r.getNumero(), Types.INTEGER);
                ps.setNull(8, Types.VARCHAR);
                ps.setNull(9, Types.DOUBLE);
            } else if (p instanceof EBook e) {
                ps.setNull(5, Types.VARCHAR);
                ps.setNull(6, Types.INTEGER);
                ps.setNull(7, Types.INTEGER);
                ps.setString(8, e.getFormato());
                ps.setObject(9, e.getTamanoMB(), Types.DOUBLE);
            } else {
                throw new IllegalArgumentException("Tipo no soportado");
            }

            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) p.setId(keys.getLong(1));
            }
            return p;
        }
    }

    public List<Publicacion> listar() throws SQLException {
        String sql = "SELECT * FROM PUBLICACION ORDER BY ID DESC";
        try (Connection c = DerbyManager.get();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Publicacion> out = new ArrayList<>();
            while (rs.next()) out.add(fromRow(rs));
            return out;
        }
    }

    public List<Publicacion> buscarPorTitulo(String q) throws SQLException {
        String sql = "SELECT * FROM PUBLICACION WHERE UPPER(TITULO) LIKE ? ORDER BY ID DESC";
        try (Connection c = DerbyManager.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, "%" + q.toUpperCase() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                List<Publicacion> out = new ArrayList<>();
                while (rs.next()) out.add(fromRow(rs));
                return out;
            }
        }
    }

    private Publicacion fromRow(ResultSet rs) throws SQLException {
        String tipo = rs.getString("TIPO");
        Long id     = rs.getLong("ID");
        String t    = rs.getString("TITULO");
        String a    = rs.getString("AUTOR");
        int anio    = rs.getInt("ANIO");

        return switch (tipo) {
            case "LIBRO"   -> new Libro(id, t, a, anio, rs.getString("ISBN"), rs.getInt("PAGINAS"));
            case "REVISTA" -> new Revista(id, t, a, anio, rs.getInt("NUMERO"));
            case "EBOOK"   -> new EBook(id, t, a, anio, rs.getString("FORMATO"), rs.getDouble("TAMANOMB"));
            default -> throw new IllegalStateException("Tipo desconocido: " + tipo);
        };
    }
}
