package web.app.caravanamedieval.model;

import jakarta.persistence.*;
import lombok.*;
import web.app.caravanamedieval.model.keys.ServicesByCityKey;

@Entity
@Table(name = "serviciosxciudad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicesByCity {

    @EmbeddedId
    private ServicesByCityKey id;

    @ManyToOne
    @MapsId("cityId")
    @JoinColumn(name = "ciudad_id")
    private City city;

    @ManyToOne
    @MapsId("serviceId")
    @JoinColumn(name = "servicio_id")
    private Services service;

    @Column(name = "precio", nullable = false)
    private Integer price;

    @Column(name = "comprado", nullable = false, length = 1)
    private boolean purchased;
}
