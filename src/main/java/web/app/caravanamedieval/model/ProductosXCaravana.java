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
public class ProductosXCaravana {

    @EmbeddedId
    private ProductosXCaravanaKey id;

    @ManyToOne
    @MapsId("caravanaId")
    @JoinColumn(name = "caravana_id")
    private Caravana caravana;

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @Column(nullable = false)
    private int cantidad;
}
