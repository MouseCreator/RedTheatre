package red.theatre.backendapp.mapper;

import red.theatre.backendapp.dto.ticket.TicketResponseDTO;
import red.theatre.backendapp.model.Ticket;

public interface TicketMapper {
    TicketResponseDTO response(Ticket ticket);
}
