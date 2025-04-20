package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyProductDTO {
    private Long productId;
    private Long cityId;
    private Long caravanId;
    private int quantity;
}
