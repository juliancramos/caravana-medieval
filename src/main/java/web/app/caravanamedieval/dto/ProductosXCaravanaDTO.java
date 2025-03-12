package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductosXCaravanaDTO {
    private Long idCaravana;
    private Long idProducto;
    private int cantidad;
}