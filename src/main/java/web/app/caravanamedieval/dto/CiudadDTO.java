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
    private Integer impuestoEntrada;
    private Long mapaId;
}

