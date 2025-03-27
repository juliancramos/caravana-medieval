package web.app.caravanamedieval.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private int quantity;
}
