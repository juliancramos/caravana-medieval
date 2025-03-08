package web.app.caravanamedieval.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Integer idProducto; 
    private String nombre;
    private String descripcion;
    private Float peso;
}
