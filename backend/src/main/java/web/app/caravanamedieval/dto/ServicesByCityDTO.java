package web.app.caravanamedieval.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicesByCityDTO {
    private Long cityId;
    private Long serviceId;
    private Integer price;
    private boolean purchased;
}
