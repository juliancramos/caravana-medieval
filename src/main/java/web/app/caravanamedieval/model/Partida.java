package web.app.caravanamedieval.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "partida")
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_partida")
    private Long idPartida;

    @Column(name = "tiempo_transcurrido", nullable = false)
    private Double tiempoTranscurrido;

    @Column(name = "tiempo_limite", nullable = false)
    private Double tiempoLimite;

    @Column(name = "ganancia_minima", nullable = false)
    private Long gananciaMinima;

    @ManyToOne
    @JoinColumn(name = "caravana_id", nullable = false)
    private Caravana caravana;

    @ManyToOne
    @JoinColumn(name = "mapa_id", nullable = false)
    private Mapa mapa;
}
