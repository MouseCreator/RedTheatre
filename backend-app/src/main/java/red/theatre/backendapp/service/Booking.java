package red.theatre.backendapp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import red.theatre.backendapp.defines.SeatStatuses;
import red.theatre.backendapp.defines.TicketStatuses;
import red.theatre.backendapp.dto.book.BookingCreateDTO;
import red.theatre.backendapp.dto.ticket.TicketResponseDTO;
import red.theatre.backendapp.dto.user.UserDetails;
import red.theatre.backendapp.exception.DataNotFoundException;
import red.theatre.backendapp.exception.ValidationException;
import red.theatre.backendapp.mapper.TicketMapper;
import red.theatre.backendapp.model.Performance;
import red.theatre.backendapp.model.Seat;
import red.theatre.backendapp.model.Ticket;
import red.theatre.backendapp.model.User;
import red.theatre.backendapp.repository.SeatRepository;
import red.theatre.backendapp.repository.TicketRepository;
import red.theatre.backendapp.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class Booking {
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final TicketMapper ticketMapper;
    @Transactional
    public TicketResponseDTO bookSeats(UserDetails userDetails, BookingCreateDTO bookingCreateDTO) {
        Long userId = userDetails.getId();
        UD.validateClient(userDetails);
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new DataNotFoundException("Користувача " + userDetails.getLogin() + " не знайдено!"));
        List<Integer> seatPositions = bookingCreateDTO.getSeats();
        Long performanceId = bookingCreateDTO.getPerformanceId();
        if (performanceId == null) {
            throw new ValidationException("Неможливо забронювати! Не вказана вистава!");
        }
        if (seatPositions == null) {
            throw new ValidationException("Неможливо забронювати! Не вказано місця.");
        }
        seatPositions = seatPositions.stream().filter(Objects::nonNull).toList();
        if (seatPositions.isEmpty()) {
            throw new ValidationException("Неможливо забронювати! Не вказано жодного місця для бронювання.");
        }
        if (seatPositions.size() > 100) {
            throw new ValidationException("Неможливо забронювати понад 100 місць.");
        }
        seatPositions = seatPositions.stream().distinct().toList();
        List<Seat> seats = seatRepository.findAllByPerformanceAndPositions(performanceId, seatPositions);

        validateSeats(seats);
        reserveAll(seats);
        List<Seat> reserved = seatRepository.saveAll(seatRepository.saveAll(seats));

        Ticket ticket = saveTicket(user, reserved);
        return ticketMapper.response(ticket);
    }

    private Ticket saveTicket(User user, List<Seat> reserved) {
        Ticket ticket = new Ticket();
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setStatus(TicketStatuses.UNPAID);
        ticket.setOwner(user);
        ticket.setPerformance(reserved.getFirst().getPerformance());
        ticket.setSeatList(reserved);
        return ticketRepository.save(ticket);
    }

    private void reserveAll(List<Seat> seatsToBook) {
        for (Seat seat : seatsToBook) {
            seat.setStatus(SeatStatuses.RESERVED);
        }
    }

    private void validateSeats(List<Seat> seatsToBook) {
        List<Integer> booked = getAlreadyReservedTickets(seatsToBook);
        if (!booked.isEmpty()) {
            Integer first = booked.getFirst();
            if (booked.size() == 1) {
                throw new ValidationException("Неможливо здійснити бронювання! Місце " + first + " вже зарезервовано!");
            }
            StringBuilder builder = new StringBuilder(first);
            for (int i = 1; i < booked.size(); i++) {
                builder.append(", ").append(booked.get(i));
            }
            throw new ValidationException("Неможливо здійснити бронювання! Місця " + builder + " вже зарезервовані!");
        }
    }

    private static List<Integer> getAlreadyReservedTickets(List<Seat> seatsToBook) {
        List<Integer> booked = new ArrayList<>();
        Performance targetPerformance = seatsToBook.getFirst().getPerformance();
        for (Seat seat : seatsToBook) {
            if (!seat.getPerformance().getId().equals(targetPerformance.getId())) {
                throw new ValidationException("Неможливо додати місця з різних вистав до одного квитка.");
            }
            if (seat.getStatus().equals("free")) {
                continue;
            }
            booked.add(seat.getPosition());
        }
        return booked;
    }
}
