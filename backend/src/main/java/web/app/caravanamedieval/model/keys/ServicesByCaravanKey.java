package web.app.caravanamedieval.model.keys;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ServicesByCaravanKey implements Serializable {

    private Long caravanId;
    private Long serviceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServicesByCaravanKey that = (ServicesByCaravanKey) o;
        return Objects.equals(caravanId, that.caravanId) &&
                Objects.equals(serviceId, that.serviceId);
    }

    //Genera numero hash combinando atributos
    @Override
    public int hashCode() {
        return Objects.hash(caravanId, serviceId);
    }
}
