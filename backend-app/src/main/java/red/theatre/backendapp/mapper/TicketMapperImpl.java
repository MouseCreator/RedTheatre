package red.theatre.backendapp.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import red.theatre.backendapp.dto.ticket.TicketResponseDTO;
import red.theatre.backendapp.model.Seat;
import red.theatre.backendapp.model.Ticket;
import red.theatre.backendapp.service.Dates;
import red.theatre.backendapp.service.Tickets;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketMapperImpl implements TicketMapper {
    private final Dates dates;
    private final Tickets tickets;
    @Override
    public TicketResponseDTO response(Ticket ticket) {
        TicketResponseDTO response = new TicketResponseDTO();

        response.setId(ticket.getId());
        response.setOwnerName(ticket.getOwner().getLogin());
        List<Integer> positions = ticket.getSeatList().stream().map(Seat::getPosition).toList();
        response.setSeatNumbers(positions);
        String performanceTime = dates.dateToString(ticket.getPerformance().getTimeDate());
        response.setDate(performanceTime);
        response.setStatus(ticket.getStatus());
        response.setPerformanceName(ticket.getPerformance().getDetails().getName());
        response.setTheatreName(ticket.getPerformance().getTheatre().getName());
        BigDecimal totalPrice = tickets.totalPrice(ticket);
        response.setTotalPrice(totalPrice);

        return response;
    }
}
