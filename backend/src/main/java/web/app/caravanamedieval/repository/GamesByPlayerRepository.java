package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.app.caravanamedieval.model.GamesByPlayer;
import web.app.caravanamedieval.model.keys.GamesByPlayerKey;

import java.util.List;

public interface GamesByPlayerRepository extends JpaRepository<GamesByPlayer, GamesByPlayerKey> {
    List<GamesByPlayer> findByPlayer_IdPlayer(long idPlayer);

    //busca todos los jugadores de una partida según el id
    @Query("SELECT g.player.idPlayer FROM GamesByPlayer g WHERE g.game.idGame = :gameId")
    List<Long> findPlayerIdsByGameId(@Param("gameId") Long gameId);
}
