package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartidaDTO {
    private Double tiempoTranscurrido;
    private Double tiempoLimite;
    private Long gananciaMinima;
    private Long caravanaId;
    private Long mapaId;
}
