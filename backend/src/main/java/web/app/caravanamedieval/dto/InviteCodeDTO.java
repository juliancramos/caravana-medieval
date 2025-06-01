package web.app.caravanamedieval.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class InviteCodeDTO {
    private String code;
    private Long gameId;
    private LocalDateTime expiresAt;

    public InviteCodeDTO(String code, Long gameId, LocalDateTime expiresAt) {
        this.code = code;
        this.gameId = gameId;
        this.expiresAt = expiresAt;
    }
}
