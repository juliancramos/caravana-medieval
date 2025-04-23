package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaravanDTO {
    private String name;
    private Double speed;
    private Double maxCapacity;
    private Long availableMoney;
    private int lifePoints;
    private Long currentCityId;
}
