package web.app.caravanamedieval.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "caravana")
public class Caravan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Se usa identity ya que en Postgre está como "serial"
    @Column(name = "id_caravana")
    private Long idCaravan;

    @Column(name = "nombre", nullable = false, length = 30)
    private String name;

    @Column(name = "velocidad", nullable = false)
    private Double speed;

    @Column(name = "capacidad_maxima", nullable = false)
    private Double maxCapacity;

    @Column(name = "dinero_disponible", nullable = false)
    private Long availableMoney;

    @Column(name = "puntos_vida", nullable = false)
    private int lifePoints;

    @Column(name = "img_url")
    private String imgUrl;

    @ManyToMany
    @JoinTable(
            name = "productosxcaravana",  // Tabla intemedia
            joinColumns = @JoinColumn(name = "caravana_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    @JsonManagedReference
    private List<Product> products;

    @OneToMany(mappedBy = "caravan")
    @JsonIgnore
    private List<Game> games;

    @ManyToOne
    @JoinColumn(name = "ciudad_actual_id", nullable = false)
    private City currentCity;
}
