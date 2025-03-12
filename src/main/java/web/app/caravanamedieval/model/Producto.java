package web.app.caravanamedieval.model;

import jakarta.persistence.*;
import lombok.*;
import web.app.caravanamedieval.model.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 50)
    private String descripcion;

    @Column(name = "peso", nullable = false)
    private Float peso;

    @ManyToMany(mappedBy = "productos") 
    private List<Caravana> caravanas; 

}
