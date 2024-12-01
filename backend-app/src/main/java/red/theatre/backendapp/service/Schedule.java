package red.theatre.backendapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import red.theatre.backendapp.dto.performance.FullPerformanceResponseDTO;
import red.theatre.backendapp.dto.performance.SimplePerformanceResponseDTO;
import red.theatre.backendapp.repository.PerformanceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Schedule {
    private final PerformanceRepository performanceRepository;

    public List<SimplePerformanceResponseDTO> getAllPerformances() {
        return List.of();
    }

    public FullPerformanceResponseDTO getPerformanceById(Long id) {
        return null;
    }
}
