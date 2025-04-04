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
public class ServicesByCityKey implements Serializable {
    private Long cityId;
    private Long serviceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServicesByCityKey that = (ServicesByCityKey) o;
        return Objects.equals(cityId, that.cityId) &&
                Objects.equals(serviceId, that.serviceId);
    }

    //Genera numero hash combinando atributos
    @Override
    public int hashCode() {
        return Objects.hash(cityId, serviceId);
    }
}
