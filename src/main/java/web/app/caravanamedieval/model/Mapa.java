package web.app.caravanamedieval.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "mapa")
public class Mapa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mapa")
    private Long idMapa;

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;

    @ManyToMany
    @JoinTable(
            name = "ciudadesxmapa",
            joinColumns = @JoinColumn(name = "mapa_id"),
            inverseJoinColumns = @JoinColumn(name = "ciudad_id")
    )
    private List<Ciudad> ciudades;

    public Mapa() {
        this.ciudades = new ArrayList<>();
    }


    public Mapa(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Long getIdMapa() {
        return idMapa;
    }

    public void setIdMapa(Long idMapa) {
        this.idMapa = idMapa;
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

    public List<Ciudad> getCiudades() {
        return ciudades;
    }
}
