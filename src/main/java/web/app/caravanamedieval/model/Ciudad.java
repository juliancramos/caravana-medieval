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
@Table(name = "ciudad")
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ciudad")
    private Long idCiudad;

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;

    @Column(name = "impuesto_entrada", nullable = false)
    private int impuestoEntrada;

    @ManyToMany(mappedBy = "ciudades")
    private List<Mapa> mapas;

    public Ciudad(){
        this.mapas = new ArrayList<>();
    }


    public Ciudad(String nombre, int impuestoEntrada) {
        this.nombre = nombre;
        this.impuestoEntrada = impuestoEntrada;
    }

    public long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(long idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImpuestoEntrada() {
        return impuestoEntrada;
    }

    public void setImpuestoEntrada(int impuestoEntrada) {
        this.impuestoEntrada = impuestoEntrada;
    }

    public List<Mapa> getMapas() {
        return mapas;
    }
}
