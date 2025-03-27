package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConexionCiudadDTO {
    private Long ciudadOrigenId;
    private Long ciudadDestinoId;
    private Double tiempoTrayecto;

    public Long getCiudadOrigenId() {
        return ciudadOrigenId;
    }

    public void setCiudadOrigenId(Long ciudadOrigenId) {
        this.ciudadOrigenId = ciudadOrigenId;
    }

    public Long getCiudadDestinoId() {
        return ciudadDestinoId;
    }

    public void setCiudadDestinoId(Long ciudadDestinoId) {
        this.ciudadDestinoId = ciudadDestinoId;
    }

    public Double getTiempoTrayecto() {
        return tiempoTrayecto;
    }

    public void setTiempoTrayecto(Double tiempoTrayecto) {
        this.tiempoTrayecto = tiempoTrayecto;
    }
}
