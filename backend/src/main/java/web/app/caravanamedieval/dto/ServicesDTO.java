package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicesDTO {
    private String name;
    private String description;
    private Float upgradePerPurchase;
    private Float maxUpgrade;

}
