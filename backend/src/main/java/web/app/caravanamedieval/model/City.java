package web.app.caravanamedieval.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ciudad")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ciudad")
    private Long idCity;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "impuesto_entrada", nullable = false)
    private int entryTax;

    @Column(name = "img_url")
    private String imgUrl;

    // Relación con el mapa (ahora directamente en la entidad Ciudad)
    @ManyToOne
    @JoinColumn(name = "mapa_id", nullable = false)
    @JsonIgnore
    private Map map;

    // Representa la ciudad de origen y de destino en las rutas
    @OneToMany(mappedBy = "originCity", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Route> originRoutes = new ArrayList<>();

    @OneToMany(mappedBy = "destinationCity", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Route> destinationRoutes = new ArrayList<>();
}
