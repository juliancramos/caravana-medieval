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
public class ProductsByCaravanKey implements Serializable {
    private Long caravanId;
    private Long productId;

    //Determina si dos objetos son iguales
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductsByCaravanKey that = (ProductsByCaravanKey) o;
        return Objects.equals(caravanId, that.caravanId) &&
                Objects.equals(productId, that.productId);
    }

    //Genera numero hash combinando atributos
    @Override
    public int hashCode() {
        return Objects.hash(caravanId, productId);
    }
}
