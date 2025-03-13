package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioDTO {

    private String nombre;
    private String descripcion;
    private Float mejoraxCompra;
    private Float mejoraMax;

}
