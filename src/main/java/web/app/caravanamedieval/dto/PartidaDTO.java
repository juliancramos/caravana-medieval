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
    private Double gananciaMinima;
    private Long caravanaId;
    private Long mapaId;

    public Double getTiempoTranscurrido() {
        return tiempoTranscurrido;
    }

    public void setTiempoTranscurrido(Double tiempoTranscurrido) {
        this.tiempoTranscurrido = tiempoTranscurrido;
    }

    public Double getTiempoLimite() {
        return tiempoLimite;
    }

    public void setTiempoLimite(Double tiempoLimite) {
        this.tiempoLimite = tiempoLimite;
    }

    public Double getGananciaMinima() {
        return gananciaMinima;
    }

    public void setGananciaMinima(Double gananciaMinima) {
        this.gananciaMinima = gananciaMinima;
    }

    public Long getCaravanaId() {
        return caravanaId;
    }

    public void setCaravanaId(Long caravanaId) {
        this.caravanaId = caravanaId;
    }

    public Long getMapaId() {
        return mapaId;
    }

    public void setMapaId(Long mapaId) {
        this.mapaId = mapaId;
    }
}
