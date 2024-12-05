package red.theatre.backendapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import red.theatre.backendapp.defines.Defines;
import red.theatre.backendapp.dto.book.BookingCreateDTO;
import red.theatre.backendapp.dto.performance.FullPerformanceResponseDTO;
import red.theatre.backendapp.dto.ticket.TicketResponseDTO;
import red.theatre.backendapp.dto.user.UserDetails;
import red.theatre.backendapp.exception.AuthForbiddenException;
import red.theatre.backendapp.exception.AuthUnauthorizedException;
import red.theatre.backendapp.exception.DataNotFoundException;
import red.theatre.backendapp.exception.ValidationException;
import red.theatre.backendapp.utils.JSON;
import red.theatre.backendapp.utils.UserDetailsFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BookingControllerTest {
    @Autowired
    private BookingController bookingController;
    @Autowired
    private UserDetailsFactory factory;

    @Test
    void getPerformanceById_ok() {
        UserDetails userDetails = factory.client();
        List<Long> testOnIds = List.of(1L, 2L, 5L, 7L, 8L);
        for (Long expectId : testOnIds) {
            ResponseEntity<FullPerformanceResponseDTO> entity = bookingController.getBookingPerformanceById(expectId, userDetails);
            assertEquals(HttpStatus.OK, entity.getStatusCode());
            FullPerformanceResponseDTO body = entity.getBody();
            String json = JSON.stringify(body);
            assertNotNull(json);
            assertEquals(expectId, body.getId());
        }
    }

    @Test
    void bookTicket_ok() {
        UserDetails userDetails = factory.client();
        BookingCreateDTO bookingCreateDTO = new BookingCreateDTO();
        bookingCreateDTO.setPerformanceId(1L);
        bookingCreateDTO.setSeats(List.of(1, 2, 3));
        ResponseEntity<TicketResponseDTO> entity = bookingController.bookTicket(bookingCreateDTO, userDetails);
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        TicketResponseDTO body = entity.getBody();
        assertEquals(0, body.getTotalPrice().compareTo(Defines.SEAT_PRICE.multiply(new BigDecimal("3"))));
        assertEquals(userDetails.getLogin(), body.getOwnerName());
    }
    @Test
    void bookTicket_1() {
        UserDetails userDetails = factory.client();
        BookingCreateDTO bookingCreateDTO = new BookingCreateDTO(1L, List.of(6));
        ResponseEntity<TicketResponseDTO> entity = bookingController.bookTicket(bookingCreateDTO, userDetails);
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        TicketResponseDTO body = entity.getBody();
        assertEquals(1, body.getSeatNumbers().size());
    }
    @Test
    void bookTicket_100() {
        UserDetails userDetails = factory.client();
        BookingCreateDTO bookingCreateDTO = new BookingCreateDTO();
        bookingCreateDTO.setPerformanceId(5L);
        List<Integer> seats = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            seats.add(i);
        }
        bookingCreateDTO.setSeats(seats);
        ResponseEntity<TicketResponseDTO> entity = bookingController.bookTicket(bookingCreateDTO, userDetails);
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        TicketResponseDTO body = entity.getBody();
        assertEquals(100, body.getSeatNumbers().size());
    }

    @Test
    void getPerformanceById_invalidId() {
        UserDetails userDetails = factory.client();
        long expectId = -1L;
        assertThrows(DataNotFoundException.class,
                () -> bookingController.getBookingPerformanceById(expectId, userDetails));
    }

    @Test
    void getPerformanceById_userNull() {
        long expectId = 1L;
        AuthUnauthorizedException aException = assertThrows(AuthUnauthorizedException.class,
                () -> bookingController.getBookingPerformanceById(expectId, null));
        assertEquals("Користувач не авторизований", aException.getMessage());
    }
    @Test
    void getPerformanceById_userGuest() {
        UserDetails userDetails = factory.guest();
        long expectId = 1L;
        AuthUnauthorizedException aException = assertThrows(AuthUnauthorizedException.class,
                () -> bookingController.getBookingPerformanceById(expectId, userDetails));
        assertEquals("Користувач не авторизований", aException.getMessage());
    }

    @Test
    void bookTicket_nullSeats() {
        UserDetails userDetails = factory.client();
        BookingCreateDTO bookingCreateDTO = new BookingCreateDTO(1L, null);
        assertThrows(ValidationException.class, () -> bookingController.bookTicket(bookingCreateDTO, userDetails));
    }

    @Test
    void bookTicket_emptySeats() {
        UserDetails userDetails = factory.client();
        BookingCreateDTO bookingCreateDTO = new BookingCreateDTO(1L, List.of());
        assertThrows(ValidationException.class, () -> bookingController.bookTicket(bookingCreateDTO, userDetails));
    }

    @Test
    void bookTicket_nullPerformance() {
        UserDetails userDetails = factory.client();
        BookingCreateDTO bookingCreateDTO = new BookingCreateDTO(1L, null);
        assertThrows(ValidationException.class, () -> bookingController.bookTicket(bookingCreateDTO, userDetails));
    }

    @Test
    void bookTicket_nullRequest() {
        UserDetails userDetails = factory.client();
        assertThrows(ValidationException.class, () -> bookingController.bookTicket(null, userDetails));
    }
    @Test
    void bookTicket_101() {
        UserDetails userDetails = factory.client();
        BookingCreateDTO bookingCreateDTO = new BookingCreateDTO();
        bookingCreateDTO.setPerformanceId(5L);
        List<Integer> seats = new ArrayList<>();
        for (int i = 1; i <= 101; i++) {
            seats.add(i);
        }
        bookingCreateDTO.setSeats(seats);
        ValidationException vException = assertThrows(ValidationException.class,
                () -> bookingController.bookTicket(bookingCreateDTO, userDetails));
        assertEquals("Неможливо забронювати понад 100 місць.", vException.getMessage());
    }

    @Test
    void bookTicket_negativeSeatPosition() {
        UserDetails userDetails = factory.client();
        BookingCreateDTO bookingCreateDTO = new BookingCreateDTO(1L, List.of(-1));
        DataNotFoundException dException = assertThrows(DataNotFoundException.class,
                () -> bookingController.bookTicket(bookingCreateDTO, userDetails));
        assertEquals("Театральний зал не має місця з номером -1", dException.getMessage());
    }

    @Test
    void bookTicket_overflowSeatPosition() {
        UserDetails userDetails = factory.client();
        BookingCreateDTO bookingCreateDTO = new BookingCreateDTO(1L, List.of(1000));
        DataNotFoundException dException = assertThrows(DataNotFoundException.class,
                () -> bookingController.bookTicket(bookingCreateDTO, userDetails));
        assertEquals("Театральний зал не має місця з номером 1000", dException.getMessage());
    }
    @Test
    void bookTicket_negativePerformance() {
        UserDetails userDetails = factory.client();
        BookingCreateDTO bookingCreateDTO = new BookingCreateDTO(-1L, List.of(1));
        DataNotFoundException dException = assertThrows(DataNotFoundException.class,
                () -> bookingController.bookTicket(bookingCreateDTO, userDetails));
        assertEquals("Не знайдено виставу з ідентифікатором -1", dException.getMessage());
    }

    @Test
    void bookTicket_emptyAuth() {
        BookingCreateDTO bookingCreateDTO = new BookingCreateDTO(1L, List.of(1));
        AuthUnauthorizedException authException = assertThrows(AuthUnauthorizedException.class,
                () -> bookingController.bookTicket(bookingCreateDTO, null));
        assertEquals("Користувач не авторизований", authException.getMessage());
    }

    @Test
    void bookTicket_adminAuth() {
        UserDetails userDetails = factory.admin();
        BookingCreateDTO bookingCreateDTO = new BookingCreateDTO(1L, List.of(1));
        assertThrows(AuthForbiddenException.class,
                () -> bookingController.bookTicket(bookingCreateDTO, userDetails));
    }

    @Test
    void bookTicket_GuestAuth() {
        UserDetails userDetails = factory.guest();
        BookingCreateDTO bookingCreateDTO = new BookingCreateDTO(1L, List.of(1));
        assertThrows(AuthUnauthorizedException.class,
                () -> bookingController.bookTicket(bookingCreateDTO, userDetails));
    }

    @Test
    void bookTicket_alreadyBooked() {
        UserDetails userDetails = factory.client();
        BookingCreateDTO bookingCreateDTO = new BookingCreateDTO(1L, List.of(4));
        bookingController.bookTicket(bookingCreateDTO, userDetails);
        assertThrows(ValidationException.class, ()-> bookingController.bookTicket(bookingCreateDTO, userDetails));
    }
}