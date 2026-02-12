package web.app.caravanamedieval.service;

import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.InviteCodeDTO;
import web.app.caravanamedieval.model.Game;
import web.app.caravanamedieval.model.InviteCode;
import web.app.caravanamedieval.repository.GameRepository;
import web.app.caravanamedieval.repository.InviteCodeRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class InviteCodeService {

    private static final int CODE_LENGTH = 15;
    private static final int EXPIRATION_MINUTES = 30;

    private final InviteCodeRepository inviteCodeRepository;
    private final GameRepository gameRepository;


    public InviteCodeService(InviteCodeRepository inviteCodeRepository, GameRepository gameRepository) {
        this.inviteCodeRepository = inviteCodeRepository;
        this.gameRepository = gameRepository;
    }

    public InviteCodeDTO generateOrReuseCode(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        //busca en BD el código más reciente generado para una partida específica
        Optional<InviteCode> existing = inviteCodeRepository.findTopByGame_IdGameOrderByCreatedAtDesc(gameId);

        //vericia si existe el codigo y si está activo
        if (existing.isPresent() && existing.get().getExpiresAt().isAfter(LocalDateTime.now())) {
            InviteCode code = existing.get();
            //si está activo lo retorna
            return new InviteCodeDTO(code.getCode(), gameId, code.getExpiresAt());
        }
        //crea un nuevo codigo
        InviteCode newCode = new InviteCode();
        newCode.setCode(generateRandomCode());
        newCode.setGame(game);
        newCode.setCreatedAt(LocalDateTime.now());
        //para el límite de tiempo en el que estará activo
        newCode.setExpiresAt(LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES));

        inviteCodeRepository.save(newCode);

        return new InviteCodeDTO(newCode.getCode(), gameId, newCode.getExpiresAt());
    }

    public Optional<Integer> validateCode(String code) {
        return inviteCodeRepository.findByCode(code)
                .filter(c -> c.getExpiresAt().isAfter(LocalDateTime.now()))
                .map(c -> Math.toIntExact(c.getGame().getIdGame()));
    }


    //genera un código random para que sea el codigo que se compartirá
    private String generateRandomCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < CODE_LENGTH; i++) {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }

        return result.toString();
    }
}
