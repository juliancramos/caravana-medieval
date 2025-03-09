package web.app.caravanamedieval.model;

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
    private double velocidad;

    @Column(name = "capacidad_maxima", nullable = false)
    private double capacidadMaxima;

    @Column(name = "dinero_disponible", nullable = false)
    private long dineroDisponible;

    @Column(name = "puntos_vida", nullable = false)
    private int puntosVida;

    @ManyToMany
    private List<Producto> productos; // 🔹 Relación de muchos a muchos


}
