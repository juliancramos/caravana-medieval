// GameByPlayerDTO.java
package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.app.caravanamedieval.model.Game;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameByPlayerDTO {
    private Game game;
}
