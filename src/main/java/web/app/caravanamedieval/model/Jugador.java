package web.app.caravanamedieval.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jugador")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jugador")
    private Integer idJugador;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "rol_jugador", nullable = false)
    private String rol;

    @OneToOne
    @JoinColumn(name = "id_partida", unique = true, nullable = true)
    private Partida partida;
}
