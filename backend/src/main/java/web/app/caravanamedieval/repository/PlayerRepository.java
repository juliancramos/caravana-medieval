package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.app.caravanamedieval.model.Player;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByUsername(String username);

    //busca todos los roles según los ids de jugadores
    @Query("SELECT j.role FROM Player j WHERE j.idPlayer IN :ids")
    List<String> findRolesByPlayerIds(@Param("ids") List<Long> ids);
}
