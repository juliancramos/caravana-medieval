package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RutaDTO {
    private String tipo;
    private Long ciudadOrigenId;
    private Long ciudadDestinoId;
    private Integer dano;
    private String causaDano;
    private int tiempoTrayecto;
}

