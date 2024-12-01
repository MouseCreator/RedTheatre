package red.theatre.backendapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import red.theatre.backendapp.dto.performance.FullPerformanceResponseDTO;
import red.theatre.backendapp.dto.performance.SimplePerformanceResponseDTO;

import red.theatre.backendapp.service.Schedule;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final Schedule schedule;
    @GetMapping
    public ResponseEntity<List<SimplePerformanceResponseDTO>> getAllPerformances() {
        List<SimplePerformanceResponseDTO> allPerformances = schedule.getAllPerformances();
        return ResponseEntity.status(HttpStatus.OK).body(allPerformances);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullPerformanceResponseDTO> getPerformanceById(@PathVariable("id") Long id) {
        FullPerformanceResponseDTO performanceById = schedule.getPerformanceById(id);
        return ResponseEntity.status(HttpStatus.OK).body(performanceById);
    }
}
