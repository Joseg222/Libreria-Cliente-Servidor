package app.model;

public class EBook extends Publicacion implements Reproducible {
    private String formato;
    private double tamanoMB;

    public EBook() {}
    public EBook(Long id, String titulo, String autor, int anio, String formato, double tamanoMB) {
        super(id, titulo, autor, anio);
        this.formato = formato; this.tamanoMB = tamanoMB;
    }

    @Override
    public String getTipo() { return "EBOOK"; }

    public String getFormato() { return formato; }
    public void setFormato(String formato) { this.formato = formato; }
    public double getTamanoMB() { return tamanoMB; }
    public void setTamanoMB(double tamanoMB) { this.tamanoMB = tamanoMB; }

    @Override
    public String reproducir() {
        return "Reproduciendo eBook " + getTitulo() + " en " + formato + " (" + tamanoMB + " MB)";
    }
}
