package red.theatre.backendapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public List<SimplePerformanceResponseDTO> getAllPerformances() {
        return schedule.getAllPerformances();
    }
    @GetMapping("/{id}")
    public FullPerformanceResponseDTO getPerformanceById(@PathVariable("id") Long id) {
        return schedule.getPerformanceById(id);
    }
}
