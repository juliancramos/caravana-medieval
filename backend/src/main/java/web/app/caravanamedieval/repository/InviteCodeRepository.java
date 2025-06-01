package web.app.caravanamedieval.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.app.caravanamedieval.model.InviteCode;

import java.util.Optional;

@Repository
public interface InviteCodeRepository extends JpaRepository<InviteCode, Integer> {
    Optional<InviteCode> findTopByGame_IdGameOrderByCreatedAtDesc(Long gameId);
    Optional<InviteCode> findByCode(String code);
}
