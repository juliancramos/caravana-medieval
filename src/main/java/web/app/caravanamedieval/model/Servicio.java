package web.app.caravanamedieval.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "servicio")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Integer idServicio;

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 1000)
    private String descripcion;

    @Column(name = "mejoraxCompra", nullable = false)
    private Float mejoraxCompra;

    @Column(name = "mejora_max", nullable = false)
    private Float mejoraMax;

   
   

 
    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
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

    public Float getMejoraxCompra() {
        return mejoraxCompra;
    }

    public void setMejoraxCompra(Float mejoraxCompra) {
        this.mejoraxCompra = mejoraxCompra;
    }

    public Float getMejoraMax() {
        return mejoraMax;
    }

    public void setMejoraMax(Float mejoraMax) {
        this.mejoraMax = mejoraMax;
    }

  
}
