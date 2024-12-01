package red.theatre.backendapp.dto.book;

import lombok.Data;

import java.util.List;

@Data
public class BookingCreateDTO {
    private Long performanceId;
    private List<Integer> seats;
}
