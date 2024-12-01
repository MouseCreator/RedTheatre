package red.theatre.backendapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import red.theatre.backendapp.dto.performance.SimplePerformanceResponseDTO;
import red.theatre.backendapp.service.Dates;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ScheduleControllerTest {
    @Autowired
    private ScheduleController scheduleController;
    @Autowired
    private Dates dates;

    @Test
    void getAllPerformances_ok() {
        ResponseEntity<List<SimplePerformanceResponseDTO>> entity = scheduleController.getAllPerformances();
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        List<SimplePerformanceResponseDTO> body = entity.getBody();
        assertNotNull(body);
        assertFalse(body.isEmpty());
        SimplePerformanceResponseDTO prev = body.getFirst();
        for (int i = 1; i < body.size(); i++) {
            SimplePerformanceResponseDTO current = body.get(i);
            LocalDateTime currentDate = dates.stringToDate(current.getDate());
            LocalDateTime prevDate = dates.stringToDate(prev.getDate());
            assertTrue(currentDate.isAfter(prevDate) || currentDate.isEqual(prevDate));
        }
    }

}