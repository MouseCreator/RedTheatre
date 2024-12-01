package red.theatre.backendapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import red.theatre.backendapp.dto.book.BookingCreateDTO;
import red.theatre.backendapp.dto.performance.FullPerformanceResponseDTO;
import red.theatre.backendapp.dto.ticket.TicketResponseDTO;
import red.theatre.backendapp.dto.user.UserDetails;
import red.theatre.backendapp.service.Booking;
import red.theatre.backendapp.service.Schedule;
import red.theatre.backendapp.service.UD;
@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final Schedule schedule;
    private final Booking booking;
    @GetMapping("/{id}")
    public ResponseEntity<FullPerformanceResponseDTO> getBookingPerformanceById(@PathVariable("id") Long id,
                                                                                @RequestAttribute("user") UserDetails userDetails) {
        UD.validateNotGuest(userDetails);
        FullPerformanceResponseDTO performanceById = schedule.getPerformanceById(id);
        return ResponseEntity.status(HttpStatus.OK).body(performanceById);
    }

    @PostMapping
    public ResponseEntity<TicketResponseDTO> createTicket(@RequestBody BookingCreateDTO bookingCreateDTO,
                                                          @RequestAttribute("user") UserDetails userDetails) {
        TicketResponseDTO responseDTO = booking.bookSeats(userDetails, bookingCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
