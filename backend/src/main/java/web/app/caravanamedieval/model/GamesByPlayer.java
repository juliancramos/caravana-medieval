package web.app.caravanamedieval.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
    @Entity
    @Table (name = "partidasxjugador")
    public class GamesByPlayer {
        @EmbeddedId
        private GamesByPlayerKey id;

        @ManyToOne
        @MapsId("gameId")
        @JoinColumn(name = "partida_id")
        private Game game;

        @ManyToOne
        @MapsId("playerId")
        @JoinColumn(name = "jugador_id")
        private Player player;
    }
