package web.app.caravanamedieval.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import web.app.caravanamedieval.model.City;

@Data
@AllArgsConstructor
//Se usa ya que el context en ruta falla si se tienen dos atributos de el mismo tipo de dato (ciudad)
public class RouteContext {
    private final City originCity;
    private final City destinationCity;
}
