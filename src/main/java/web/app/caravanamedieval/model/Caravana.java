package web.app.caravanamedieval.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import web.app.caravanamedieval.model.*;
import java.util.List;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "caravana")
public class Caravana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Se usa identity ya que en Postgre está como "serial"
    @Column(name = "id_caravana")
    private Long idCaravana;

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;

    @Column(name = "velocidad", nullable = false)
    private Double velocidad;

    @Column(name = "capacidad_maxima", nullable = false)
    private Double capacidadMaxima;

    @Column(name = "dinero_disponible", nullable = false)
    private Long dineroDisponible;

    @Column(name = "puntos_vida", nullable = false)
    private int puntosVida;

    @ManyToMany
    @JoinTable(
            name = "productosxcaravana",  // Tabla intemedia
            joinColumns = @JoinColumn(name = "caravana_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    @JsonManagedReference
    private List<Producto> productos;

    @OneToMany(mappedBy = "caravana")
    @JsonIgnore
    private List<Partida> partidas;



}
