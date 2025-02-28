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
    private Ciudad ciudadOrigen;

    @ManyToOne
    @JoinColumn(name = "ciudad_destino_id", nullable = false)
    private Ciudad ciudadDestino;


    public ConexionCiudad(Ciudad ciudadOrigen, Ciudad ciudadDestino, Double tiempoTrayecto) {
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
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

    public Ciudad getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(Ciudad ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public Ciudad getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(Ciudad ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }
}
