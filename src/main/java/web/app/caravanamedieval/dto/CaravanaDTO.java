package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaravanaDTO {
    private Long idCaravana;  
    private String nombre;
    private Double velocidad;
    private Double capacidadMaxima;
    private Long dineroDisponible;
    private int puntosVida;

    // 🔹 Constructor sin `idCaravana`
    public CaravanaDTO(String nombre, Double velocidad, Double capacidadMaxima, Long dineroDisponible, Integer puntosVida) {
        this.nombre = nombre;
        this.velocidad = velocidad;
        this.capacidadMaxima = capacidadMaxima;
        this.dineroDisponible = dineroDisponible;
        this.puntosVida = puntosVida;
    }
}
