package red.theatre.backendapp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import red.theatre.backendapp.defines.SeatStatuses;
import red.theatre.backendapp.defines.TicketStatuses;
import red.theatre.backendapp.dto.ticket.TicketChangeStatusDTO;
import red.theatre.backendapp.dto.ticket.TicketResponseDTO;
import red.theatre.backendapp.dto.user.UserDetails;
import red.theatre.backendapp.exception.AuthForbiddenException;
import red.theatre.backendapp.exception.DataNotFoundException;
import red.theatre.backendapp.exception.ValidationException;
import red.theatre.backendapp.mapper.TicketMapper;
import red.theatre.backendapp.model.Seat;
import red.theatre.backendapp.model.Ticket;
import red.theatre.backendapp.repository.SeatRepository;
import red.theatre.backendapp.repository.TicketRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TicketHistory {
    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final TicketMapper ticketMapper;
    public List<TicketResponseDTO> getAllTickets(UserDetails userDetails) {
        UD.validateClient(userDetails);
        Long id = userDetails.getId();
        List<Ticket> allMyTickets = ticketRepository.findAllByOwner(id);
        return allMyTickets.stream()
                .map(ticketMapper::response)
                .toList();
    }
    @Transactional
    public TicketResponseDTO updateTicket(UserDetails userDetails, TicketChangeStatusDTO newStatusDTO) {
        UD.validateClient(userDetails);
        Long ticketId = newStatusDTO.getTicketId();
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(()->new DataNotFoundException("Неможливо знайти квиток з ідентифікатором " + ticketId));
        validateOwner(userDetails, ticket);
        String newStatus = newStatusDTO.getStatus();
        if (newStatus == null) {
            throw new ValidationException("Неможливо змінити статус на невизачене значення");
        }
        if (newStatus.equals(TicketStatuses.PAID)) {
            return payTicket(ticket);
        }
        else if (newStatus.equals(TicketStatuses.CANCELLED)) {
            return cancelTicket(ticket);
        } else {
            throw new ValidationException("Непередбачена зміна статуса квитка на " + newStatus);
        }
    }
    private TicketResponseDTO payTicket(Ticket ticket) {
        ticket.setStatus(TicketStatuses.PAID);
        Ticket saved = ticketRepository.save(ticket);
        return ticketMapper.response(saved);
    }
    private TicketResponseDTO cancelTicket(Ticket ticket) {
        List<Seat> seatList = ticket.getSeatList();
        for (Seat seat : seatList) {
            seat.setStatus(SeatStatuses.FREE);
        }
        seatRepository.saveAll(seatList);
        ticket.setStatus(TicketStatuses.CANCELLED);
        ticketRepository.delete(ticket);
        return ticketMapper.response(ticket);
    }



    private void validateOwner(UserDetails userDetails, Ticket ticket) {
        if (!Objects.equals(userDetails.getId(), ticket.getId())) {
            throw new AuthForbiddenException("Тільки власник квитка має право змінювати квиток!");
        }
    }
}
