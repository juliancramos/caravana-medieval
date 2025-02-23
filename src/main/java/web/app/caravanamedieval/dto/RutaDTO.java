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
    private long idRuta;
    private String nombre;
    private Long ciudadOrigenId;
    private Long ciudadDestinoId;
    private Integer dano; //Se usa Integer ya que puede estar vacío
    private String causaDano;

    public long getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(long idRuta) {
        this.idRuta = idRuta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

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

    public Integer getDano() {
        return dano;
    }

    public void setDano(Integer dano) {
        this.dano = dano;
    }

    public String getCausaDano() {
        return causaDano;
    }

    public void setCausaDano(String causaDano) {
        this.causaDano = causaDano;
    }
}
