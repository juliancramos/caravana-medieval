package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private List<CiudadDTO> ciudades;
}
