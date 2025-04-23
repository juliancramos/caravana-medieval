package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicesByCaravanDTO {
    private Long caravanId;
    private Long serviceId;
    private Integer currentUpdate;
}

