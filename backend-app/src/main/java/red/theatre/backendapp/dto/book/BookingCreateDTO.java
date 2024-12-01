package red.theatre.backendapp.dto.book;

import lombok.Data;

import java.util.List;

@Data
public class BookingCreateDTO {
    private List<Long> seats;
}
