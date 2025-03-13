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
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ruta")
    private int idRuta;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "ciudad_origen_id", nullable = false)
    private Ciudad ciudadOrigen;

    @ManyToOne
    @JoinColumn(name = "ciudad_destino_id", nullable = false)
    private Ciudad ciudadDestino;

    @Column(name = "dano")
    private Float dano;

    @Column(name = "causa_dano", length = 100)
    private String causaDano;

    @Column(name = "tiempo_trayecto", nullable = false)
    private int tiempoTrayecto;
}
