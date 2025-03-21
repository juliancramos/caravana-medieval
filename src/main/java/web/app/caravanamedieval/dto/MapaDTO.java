package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapaDTO {
    private String nombre;
    private String descripcion;
    private List<Long> ciudadesIds;  // IDs de las ciudades que pertenecen al mapa
    private List<Long> partidasIds;  // IDs de las partidas asociadas al mapa
}
