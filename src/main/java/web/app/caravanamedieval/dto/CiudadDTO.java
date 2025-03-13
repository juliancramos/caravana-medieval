package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CiudadDTO {
    private String nombre;
    private int impuestoEntrada;
    private Long mapaId;       // ID del mapa asociado
    private List<Long> rutasOrigenIds;  // IDs de las rutas donde la ciudad es origen
    private List<Long> rutasDestinoIds; // IDs de las rutas donde la ciudad es destino
}
