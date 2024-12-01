package red.theatre.backendapp.dto.ticket;

import lombok.Data;

@Data
public class TicketChangeStatusDTO {
    private Long ticketId;
    private String status;
}
