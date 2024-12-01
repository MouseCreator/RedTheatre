package red.theatre.backendapp.service;

import org.springframework.stereotype.Service;
import red.theatre.backendapp.model.Seat;
import red.theatre.backendapp.model.Ticket;

import java.math.BigDecimal;
import java.util.List;

@Service
public class Tickets {
    public BigDecimal totalPrice(Ticket ticket) {
        List<Seat> seatList = ticket.getSeatList();
        BigDecimal result = new BigDecimal("0.00");
        for (Seat seat : seatList) {
            BigDecimal price = seat.getPrice();
            result = result.add(price);
        }
        return result;
    }
}
