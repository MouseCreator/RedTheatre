package red.theatre.backendapp.dto.book;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BookingCreateDTO {
    private Long performanceId;
    private List<Integer> seats;

    public BookingCreateDTO(Long performanceId, List<Integer> seats) {
        this.performanceId = performanceId;
        this.seats = seats;
    }
}
