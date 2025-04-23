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
    private Integer quantity;
    private Long supplyFactor;
    private Long demandFactor;
}
