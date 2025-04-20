package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.app.caravanamedieval.model.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellProductDTO {
    private Product product;
    private int quantity;
    private long price; // FD/(1+S)
}

