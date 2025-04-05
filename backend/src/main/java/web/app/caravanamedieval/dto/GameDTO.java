package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {
    private Double elapsedTime;
    private Double timeLimit;
    private Long minProfit;
    private Long caravanId;
    private Long mapId;
}
