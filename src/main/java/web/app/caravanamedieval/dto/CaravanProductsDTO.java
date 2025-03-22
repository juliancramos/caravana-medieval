package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaravanProductsDTO {
    private Long idCaravan;
    private List<ProductsByCaravanDTO> products;
}
