package red.theatre.backendapp.dto.performance;

import lombok.Data;

@Data
public class SimplePerformanceResponseDTO {
    private Long id;
    private String theatreName;
    private String performanceName;
    private String date;
}
