package app.model;

public class Revista extends Publicacion implements Reproducible {
    private int numero;

    public Revista() {}
    public Revista(Long id, String titulo, String autor, int anio, int numero) {
        super(id, titulo, autor, anio);
        this.numero = numero;
    }

    @Override
    public String getTipo() { return "REVISTA"; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    @Override
    public String reproducir() {
        return "Visualizando revista " + getTitulo() + " N°" + numero;
    }
}
