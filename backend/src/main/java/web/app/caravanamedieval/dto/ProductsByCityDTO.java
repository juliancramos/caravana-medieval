package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsByCityDTO {
    private Long cityId;
    private Long productId;
    private Integer cantidad;
    private Long supplyFactor;
    private Long demandFactor;
}
