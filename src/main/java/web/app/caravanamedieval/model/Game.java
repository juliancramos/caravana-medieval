package web.app.caravanamedieval.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "partida")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_partida")
    private Long idGame;

    @Column(name = "tiempo_transcurrido", nullable = false)
    private Double elapsedTime;

    @Column(name = "tiempo_limite", nullable = false)
    private Double timeLimit;

    @Column(name = "ganancia_minima", nullable = false)
    private Long minProfit;

    @ManyToOne
    @JoinColumn(name = "caravana_id", nullable = false)
    @JsonManagedReference
    private Caravan caravan;

    @ManyToOne
    @JoinColumn(name = "mapa_id", nullable = false)
    @JsonManagedReference
    private Map map;
}
