package red.theatre.backendapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import red.theatre.backendapp.dto.details.PerformanceDetailsCreateDTO;
import red.theatre.backendapp.mapper.PerformanceDetailsMapper;
import red.theatre.backendapp.model.PerformanceDetails;
import red.theatre.backendapp.repository.PerformanceDetailsRepository;

@Service
@RequiredArgsConstructor
public class PerformanceDetailsService {
    private final PerformanceDetailsMapper performanceDetailsMapper;
    private final PerformanceDetailsRepository performanceDetailsRepository;

    public PerformanceDetails createPerformanceDetails(PerformanceDetailsCreateDTO createDTO) {
        PerformanceDetails performanceDetails = performanceDetailsMapper.fromCreateDTO(createDTO);
        return performanceDetailsRepository.save(performanceDetails);
    }
}
