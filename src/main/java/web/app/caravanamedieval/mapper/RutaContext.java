package web.app.caravanamedieval.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.app.caravanamedieval.model.Ciudad;

@Data
@AllArgsConstructor
//Se usa ya que el context en ruta falla si se tienen dos atributos de el mismo tipo de dato (ciudad)
public class RutaContext {
    private final Ciudad ciudadOrigen;
    private final Ciudad ciudadDestino;
}
