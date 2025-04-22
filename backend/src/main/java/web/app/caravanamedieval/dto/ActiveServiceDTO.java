// dto/ActiveServiceDTO.java
package web.app.caravanamedieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveServiceDTO {
    private String name;
    private String description;
    private String imgUrl;
}
