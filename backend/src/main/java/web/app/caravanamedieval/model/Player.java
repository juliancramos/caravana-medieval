package web.app.caravanamedieval.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jugador")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jugador")
    private Integer idPlayer;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "rol", nullable = false)
    private String role;



}
