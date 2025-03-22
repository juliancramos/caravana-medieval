package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RouteDTO {
    private String type;
    private Long originCityId;
    private Long destinationCityId;
    private Integer damage;
    private String damageCause;
    private int travelTime;
}

