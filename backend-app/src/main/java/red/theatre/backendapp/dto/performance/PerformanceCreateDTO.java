package red.theatre.backendapp.dto.performance;

import lombok.Data;

import java.util.List;

@Data
public class PerformanceCreateDTO {
    private Long id;
    private Long theatreId;
    private Long detailsId;
    private String date;
    private String pictureUrl;
    private List<Long> figures;
}
