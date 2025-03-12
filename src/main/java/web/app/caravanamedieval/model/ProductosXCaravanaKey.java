package web.app.caravanamedieval.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProductosXCaravanaKey implements Serializable {
    private Long caravanaId;
    private Long productoId;

    //Determina si dos objetos son iguales
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductosXCaravanaKey that = (ProductosXCaravanaKey) o;
        return Objects.equals(caravanaId, that.caravanaId) &&
                Objects.equals(productoId, that.productoId);
    }

    //Genera numero hash combinando atributos
    @Override
    public int hashCode() {
        return Objects.hash(caravanaId, productoId);
    }
}
