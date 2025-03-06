package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaravanaDTO {
    private String nombre;
    private double velocidad;
    private double capacidadMaxima;
    private long dineroDisponible;
    private int puntosVida;
}