package app.model;

public class Libro extends Publicacion implements Reproducible {
    private String isbn;
    private int paginas;

    public Libro() {}
    public Libro(Long id, String titulo, String autor, int anio, String isbn, int paginas) {
        super(id, titulo, autor, anio);
        this.isbn = isbn; this.paginas = paginas;
    }

    @Override
    public String getTipo() { return "LIBRO"; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public int getPaginas() { return paginas; }
    public void setPaginas(int paginas) { this.paginas = paginas; }

    @Override
    public String reproducir() {
        return "Abriendo libro: " + getTitulo() + " (" + paginas + " págs.)";
    }
}
