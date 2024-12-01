package red.theatre.backendapp.dto.performance;

import lombok.Data;

import java.util.List;

@Data
public class PerformanceCreateDTO {
    private Long theatreId;
    private Long detailsId;
    private String date;
    private Long director;
    private List<Long> figures;
}
