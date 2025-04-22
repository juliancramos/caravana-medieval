package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveServiceEffectDTO {
    private String name;
    private Float improvementPerPurchase;
    private Integer currentUpgrade;



}