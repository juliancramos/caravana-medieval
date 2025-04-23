package web.app.caravanamedieval.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.app.caravanamedieval.model.keys.ProductsByCaravanKey;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="productosxcaravana")
public class ProductsByCaravan {

    @EmbeddedId
    private ProductsByCaravanKey id;

    @ManyToOne
    @MapsId("caravanId")
    @JoinColumn(name = "caravana_id")
    private Caravan caravan;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "producto_id")
    private Product product;

    @Column(name = "cantidad", nullable = false)
    private int quantity;

    public ProductsByCaravan(Caravan caravan, Product product, int quantity) {
        this.caravan = caravan;
        this.product = product;
        this.quantity = quantity;
        this.id = new ProductsByCaravanKey(caravan.getIdCaravan(), product.getIdProduct());
    }

}
