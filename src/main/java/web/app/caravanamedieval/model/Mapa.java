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
@Table(name = "mapa")
public class Mapa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mapa")
    private Long idMapa;

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;

    @OneToMany(mappedBy = "mapa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Ciudad> ciudades = new ArrayList<>();

    // Relación con Partida
    @OneToMany(mappedBy = "mapa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Partida> partidas = new ArrayList<>();
}
