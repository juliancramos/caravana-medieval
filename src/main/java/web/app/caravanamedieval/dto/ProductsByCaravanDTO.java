package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsByCaravanDTO {
    private Long idCaravan;
    private Long idProduct;
    private int quantity;
}