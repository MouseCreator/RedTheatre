package red.theatre.backendapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
    @PutMapping
    public TicketResponseDTO updateTicket(@RequestParam TicketChangeStatusDTO ticketChangeStatusDTO,
                                          @RequestAttribute UserDetails userDetails) {
        return ticketHistory.updateTicket(userDetails, ticketChangeStatusDTO);
    }
}
