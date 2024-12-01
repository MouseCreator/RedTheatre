package red.theatre.backendapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import red.theatre.backendapp.dto.performance.FullPerformanceResponseDTO;
import red.theatre.backendapp.dto.user.UserDetails;
import red.theatre.backendapp.exception.AuthForbiddenException;
import red.theatre.backendapp.exception.AuthUnauthorizedException;
import red.theatre.backendapp.exception.DataNotFoundException;
import red.theatre.backendapp.service.Dates;
import red.theatre.backendapp.utils.UserDetailsFactory;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BookingControllerTest {
    @Autowired
    private BookingController bookingController;
    @Autowired
    private Dates dates;
    @Autowired
    private UserDetailsFactory factory;

    @Test
    void getPerformanceById_ok() {
        UserDetails userDetails = factory.client();
        long expectId = 1L;
        ResponseEntity<FullPerformanceResponseDTO> entity = bookingController.getBookingPerformanceById(expectId, userDetails);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        FullPerformanceResponseDTO body = entity.getBody();
        assertEquals(expectId, body.getId());
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
        assertThrows(AuthUnauthorizedException.class,
                () -> bookingController.getBookingPerformanceById(expectId, null));
    }

    @Test
    void getPerformanceById_userAdmin() {
        UserDetails userDetails = factory.admin();
        long expectId = -1L;
        assertThrows(AuthForbiddenException.class,
                () -> bookingController.getBookingPerformanceById(expectId, userDetails));
    }
    @Test
    void getPerformanceById_userGuest() {
        UserDetails userDetails = factory.guest();
        long expectId = 1L;
        assertThrows(AuthUnauthorizedException.class,
                () -> bookingController.getBookingPerformanceById(expectId, userDetails));
    }
}