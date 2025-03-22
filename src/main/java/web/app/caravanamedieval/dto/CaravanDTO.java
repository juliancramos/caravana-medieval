package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaravanDTO {
    private Long idCaravan;
    private String name;
    private Double speed;
    private Double maxCapacity;
    private Long availableMoney;
    private int lifePoints;

    public CaravanDTO(String name, Double speed, Double maxCapacity, Long availableMoney, Integer lifePoints) {
        this.name = name;
        this.speed = speed;
        this.maxCapacity = maxCapacity;
        this.availableMoney = availableMoney;
        this.lifePoints = lifePoints;
    }
}
