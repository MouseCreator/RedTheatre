package red.theatre.backendapp.mapper;

import red.theatre.backendapp.dto.details.PerformanceDetailsCreateDTO;
import red.theatre.backendapp.model.PerformanceDetails;

public interface PerformanceDetailsMapper {
    PerformanceDetails fromCreateDTO(PerformanceDetailsCreateDTO createDTO);
}
