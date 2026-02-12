package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceForStoreDTO {
    private Long serviceId;
    private String name;
    private String description;
    private String imgUrl;
    private int price;
    private boolean comprado;
}
