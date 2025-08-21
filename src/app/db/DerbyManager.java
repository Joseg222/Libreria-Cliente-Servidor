package app.db;

import java.sql.*;

public class DerbyManager {
    private static final String URL = "jdbc:derby:librarydb;create=true"; // crea si no existe
    private static final String TBL =
        "CREATE TABLE PUBLICACION (" +
        " ID BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
        " TIPO VARCHAR(16) NOT NULL," +           // LIBRO, REVISTA, EBOOK
        " TITULO VARCHAR(255) NOT NULL," +
        " AUTOR VARCHAR(255) NOT NULL," +
        " ANIO INT NOT NULL," +
        " ISBN VARCHAR(32)," +                    // solo LIBRO
        " PAGINAS INT," +                         // solo LIBRO
        " NUMERO INT," +                          // solo REVISTA
        " FORMATO VARCHAR(16)," +                 // solo EBOOK
        " TAMANOMB DOUBLE" +                      // solo EBOOK
        ")";

    static {
        try {
            // Cargar driver Derby
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

            try (Connection c = get(); Statement st = c.createStatement()) {
                if (!tableExists(c, "PUBLICACION")) {
                    st.executeUpdate(TBL);
                    System.out.println("Tabla PUBLICACION creada.");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private static boolean tableExists(Connection c, String name) throws SQLException {
        DatabaseMetaData md = c.getMetaData();
        try (ResultSet rs = md.getTables(null, null, name.toUpperCase(), null)) {
            return rs.next();
        }
    }
}
