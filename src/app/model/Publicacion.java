package app.model;

import java.io.Serializable;
import java.util.Objects;

public abstract class Publicacion implements Serializable {
    private Long id;
    private String titulo;
    private String autor;
    private int anio;

    public Publicacion() {}
    public Publicacion(Long id, String titulo, String autor, int anio) {
        this.id = id; this.titulo = titulo; this.autor = autor; this.anio = anio;
    }

    public abstract String getTipo();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    @Override
    public String toString() {
        return "[" + getTipo() + "] " + titulo + " - " + autor + " (" + anio + ") id=" + id;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Publicacion)) return false;
        Publicacion that = (Publicacion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}