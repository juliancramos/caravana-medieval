package web.app.caravanamedieval.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ruta")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ruta")
    private int idRoute;

    @Column(name = "tipo", nullable = false, length = 50)
    private String type;

    @ManyToOne
    @JoinColumn(name = "ciudad_origen_id", nullable = false)
    private City originCity;

    @ManyToOne
    @JoinColumn(name = "ciudad_destino_id", nullable = false)
    private City destinationCity;

    @Column(name = "dano")
    private Float damage;

    @Column(name = "causa_dano", length = 100)
    private String damageCause;

    @Column(name = "tiempo_trayecto", nullable = false)
    private int travelTime;
}
