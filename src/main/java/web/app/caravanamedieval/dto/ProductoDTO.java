package web.app.caravanamedieval.dto;

public class ProductoDTO {
    private String nombre;
    private String descripcion;
    private Float peso;

    public ProductoDTO() {}

    public ProductoDTO(String nombre, String descripcion, Float peso) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.peso = peso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }
}
