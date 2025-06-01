package web.app.caravanamedieval.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "codigo_invitacion")
public class InviteCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_codigo")
    private Integer id;

    @Column(name = "codigo", nullable = false, unique = true)
    private String code;

    @ManyToOne(optional = false)
    @JoinColumn(name = "partida_id", nullable = false)
    private Game game;

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expira_en", nullable = false)
    private LocalDateTime expiresAt;
}
