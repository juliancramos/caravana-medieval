package web.app.caravanamedieval.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.app.caravanamedieval.model.keys.ServicesByCaravanKey;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="serviciosxcaravana")
public class ServicesByCaravan {

    @EmbeddedId
    private ServicesByCaravanKey id;

    @ManyToOne
    @MapsId("caravanId")
    @JoinColumn(name = "caravana_id")
    private Caravan caravan;

    @ManyToOne
    @MapsId("serviceId")
    @JoinColumn(name = "servicio_id")
    private Services services;

    @Column(name = "mejora_actual", nullable = false)
    private Integer currentUpdate;
}
