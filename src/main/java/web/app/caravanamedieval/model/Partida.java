package web.app.caravanamedieval.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "partida")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_partida")
    private Integer idPartida;

    @Column(name = "tiempo_transcurrido", nullable = false)
    private Double tiempoTranscurrido;

    @Column(name = "tiempo_limite", nullable = false)
    private Double tiempoLimite;

    @Column(name = "ganancia_minima", nullable = false)
    private Double gananciaMinima;

    @ManyToOne
    @JoinColumn(name = "caravana_id", nullable = false)
    private Caravana caravana;

    @ManyToOne
    @JoinColumn(name = "mapa_id", nullable = false)
    private Mapa mapa;

    public Partida() {
    }

    public Partida(Double tiempoTranscurrido, Double tiempoLimite, Double gananciaMinima, Caravana caravana, Mapa mapa) {
        this.tiempoTranscurrido = tiempoTranscurrido;
        this.tiempoLimite = tiempoLimite;
        this.gananciaMinima = gananciaMinima;
        this.caravana = caravana;
        this.mapa = mapa;
    }

    public Integer getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(Integer idPartida) {
        this.idPartida = idPartida;
    }

    public Double getTiempoTranscurrido() {
        return tiempoTranscurrido;
    }

    public void setTiempoTranscurrido(Double tiempoTranscurrido) {
        this.tiempoTranscurrido = tiempoTranscurrido;
    }

    public Double getTiempoLimite() {
        return tiempoLimite;
    }

    public void setTiempoLimite(Double tiempoLimite) {
        this.tiempoLimite = tiempoLimite;
    }

    public Double getGananciaMinima() {
        return gananciaMinima;
    }

    public void setGananciaMinima(Double gananciaMinima) {
        this.gananciaMinima = gananciaMinima;
    }

    public Caravana getCaravana() {
        return caravana;
    }

    public void setCaravana(Caravana caravana) {
        this.caravana = caravana;
    }

    public Mapa getMapa() {
        return mapa;
    }

    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }
}
