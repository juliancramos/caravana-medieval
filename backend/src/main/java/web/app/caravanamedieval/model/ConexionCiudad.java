package web.app.caravanamedieval.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "conexionciudad")
public class ConexionCiudad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conexion")
    private Long idConexion;

    @Column(name = "tiempo_trayecto", nullable = false)
    private Double tiempoTrayecto;

    @ManyToOne
    @JoinColumn(name = "ciudad_origen_id", nullable = false)
    private City cityOrigen;

    @ManyToOne
    @JoinColumn(name = "ciudad_destino_id", nullable = false)
    private City cityDestino;


    public ConexionCiudad(City cityOrigen, City cityDestino, Double tiempoTrayecto) {
        this.cityOrigen = cityOrigen;
        this.cityDestino = cityDestino;
        this.tiempoTrayecto = tiempoTrayecto;
    }


    public Long getIdConexion() {
        return idConexion;
    }

    public void setIdConexion(Long idConexion) {
        this.idConexion = idConexion;
    }

    public Double getTiempoTrayecto() {
        return tiempoTrayecto;
    }

    public void setTiempoTrayecto(Double tiempoTrayecto) {
        this.tiempoTrayecto = tiempoTrayecto;
    }

    public City getCityOrigen() {
        return cityOrigen;
    }

    public void setCityOrigen(City cityOrigen) {
        this.cityOrigen = cityOrigen;
    }

    public City getCityDestino() {
        return cityDestino;
    }

    public void setCityDestino(City cityDestino) {
        this.cityDestino = cityDestino;
    }
}
