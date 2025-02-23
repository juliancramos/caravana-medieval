package web.app.caravanamedieval.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "ruta")
public class Ruta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ruta")
    private int idRuta;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "ciudad_origen_id", nullable = false)
    private Ciudad ciudadOrigen;

    @ManyToOne
    @JoinColumn(name = "ciudad_destino_id", nullable = false)
    private Ciudad ciudadDestino;

    @Column(name = "dano")
    private Integer dano;

    @Column(name = "causa_dano", length = 50)
    private String causaDano;

    public Ruta(){}

    public Ruta(String tipo, Ciudad ciudadOrigen, Ciudad ciudadDestino, Integer dano, String causaDano) {
        this.tipo = tipo;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.dano = dano;
        this.causaDano = causaDano;
    }

    //Para ruta corta


    public Ruta(String tipo, Ciudad ciudadOrigen, Ciudad ciudadDestino) {
        this.tipo = tipo;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public Integer getDano() {
        return dano;
    }

    public void setDano(Integer dano) {
        this.dano = dano;
    }

    public String getCausaDano() {
        return causaDano;
    }

    public void setCausaDano(String causaDano) {
        this.causaDano = causaDano;
    }
}
