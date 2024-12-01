package red.theatre.backendapp.dto.ticket;

import lombok.Data;

@Data
public class TicketPopulateDTO {
    private String userLogin;
    private Long performanceId;
    private Integer numberSeats;
}
