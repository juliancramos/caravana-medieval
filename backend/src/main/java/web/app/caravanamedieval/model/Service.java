package web.app.caravanamedieval.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "servicio")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Integer idService;

    @Column(name = "nombre", nullable = false, length = 30)
    private String name;

    @Column(name = "descripcion", nullable = false, length = 1000)
    private String description;

    @Column(name = "mejora_por_compra", nullable = false)
    private Float upgradePerPurchase;

    @Column(name = "mejora_max", nullable = false)
    private Float maxUpgrade;
  
}
