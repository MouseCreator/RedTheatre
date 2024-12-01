package red.theatre.backendapp.dto.performance;

import lombok.Data;

@Data
public class PerformancePopulateDTO {
    private Long theatreId;
    private Long detailsId;
    private String date;
    private String director;
    private Long numberFigures;
}
