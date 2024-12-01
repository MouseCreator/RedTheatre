package red.theatre.backendapp.dto.details;

import lombok.Data;

@Data
public class PerformanceDetailsCreateDTO {
    private String name;
    private String description;
    private String pictureUrl;
}
