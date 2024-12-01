package red.theatre.backendapp.dto.performance;

import lombok.Data;
import red.theatre.backendapp.dto.seat.SeatResponseDTO;

import java.util.List;

@Data
public class FullPerformanceResponseDTO {
    private Long id;
    private String theatreName;
    private String performanceName;
    private String date;
    private List<String> figures;
    private List<SeatResponseDTO> seats;
}