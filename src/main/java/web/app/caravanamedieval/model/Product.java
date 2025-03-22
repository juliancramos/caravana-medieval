package web.app.caravanamedieval.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProduct;

    @Column(name = "nombre", nullable = false, length = 30)
    private String name;

    @Column(name = "descripcion", nullable = false, length = 50)
    private String description;

    @Column(name = "peso", nullable = false)
    private Float weight;

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private List<Caravan> caravans;

}
