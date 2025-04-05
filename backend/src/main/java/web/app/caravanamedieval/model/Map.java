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
public class Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mapa")
    private Long idMap;

    @Column(name = "nombre", nullable = false, length = 30)
    private String name;

    @Column(name = "descripcion", nullable = false, length = 200)
    private String description;

    @Column(name = "img_url")
    private String imgUrl;

    @OneToMany(mappedBy = "map", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<City> cities = new ArrayList<>();

    // Relación con Partida
    @OneToMany(mappedBy = "map", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Game> games = new ArrayList<>();

    public void addCity(City city) {
        cities.add(city);
        city.setMap(this);
    }

}
