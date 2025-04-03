package web.app.caravanamedieval.model.keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GamesByPlayerKey implements Serializable {
    private Long gameId;
    private Long playerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        GamesByPlayerKey that = (GamesByPlayerKey) o;
        return Objects.equals(gameId, that.gameId) && Objects.equals(playerId, that.playerId);
    }

    @Override
    public int hashCode() {return Objects.hash(gameId, playerId);}
}
