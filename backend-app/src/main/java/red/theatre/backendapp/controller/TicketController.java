package red.theatre.backendapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import red.theatre.backendapp.defines.TicketStatuses;
import red.theatre.backendapp.dto.ticket.TicketChangeStatusDTO;
import red.theatre.backendapp.dto.ticket.TicketResponseDTO;
import red.theatre.backendapp.dto.user.UserDetails;
import red.theatre.backendapp.service.TicketHistory;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketHistory ticketHistory;
    @GetMapping("/history")
    public List<TicketResponseDTO> getTicketHistory(@RequestAttribute("user")UserDetails userDetails) {
        return ticketHistory.getAllTickets(userDetails);
    }
    @PutMapping("/pay/{id}")
    public TicketResponseDTO payForTicket(@PathVariable("id") Long ticket,
                                          @RequestAttribute UserDetails userDetails) {
        TicketChangeStatusDTO ticketChangeStatusDTO = new TicketChangeStatusDTO();
        ticketChangeStatusDTO.setTicketId(ticket);
        ticketChangeStatusDTO.setStatus(TicketStatuses.PAID);
        return ticketHistory.updateTicket(userDetails, ticketChangeStatusDTO);
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelTicket(
            @PathVariable("id") Long ticket,
                                       @RequestAttribute UserDetails userDetails) {
        TicketChangeStatusDTO ticketChangeStatusDTO = new TicketChangeStatusDTO();
        ticketChangeStatusDTO.setTicketId(ticket);
        ticketChangeStatusDTO.setStatus(TicketStatuses.CANCELLED);
        ticketHistory.updateTicket(userDetails, ticketChangeStatusDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
