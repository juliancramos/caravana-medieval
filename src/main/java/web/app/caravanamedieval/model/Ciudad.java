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
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ciudad")
    private Long idCiudad;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "impuesto_entrada", nullable = false)
    private int impuestoEntrada;

    // Relación con el mapa (ahora directamente en la entidad Ciudad)
    @ManyToOne
    @JoinColumn(name = "mapa_id", nullable = false)
    @JsonIgnore
    private Mapa mapa;

    // Representa la ciudad de origen y de destino en las rutas
    @OneToMany(mappedBy = "ciudadOrigen", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Ruta> rutasOrigen = new ArrayList<>();

    @OneToMany(mappedBy = "ciudadDestino", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Ruta> rutasDestino = new ArrayList<>();
}
