package web.app.caravanamedieval.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web.app.caravanamedieval.model.keys.ProductsByCityKey;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productosxciudad")
public class ProductsByCity {
    @EmbeddedId
    private ProductsByCityKey id;

    @ManyToOne
    @MapsId("cityId")
    @JoinColumn(name = "ciudad_id")
    private City city;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "producto_id")
    private Product product;

    @Column(name = "cantidad", nullable = false)
    private Integer quantity;

    @Column(name = "factor_oferta", nullable = false)
    private Long supplyFactor;

    @Column(name = "factor_demanda", nullable = false)
    private Long demandFactor;
}
