package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaravanaDTO {
    private String nombre;
    private Double velocidad;
    private Double capacidadMaxima;
    private Long dineroDisponible;
    private Integer puntosVida;
}