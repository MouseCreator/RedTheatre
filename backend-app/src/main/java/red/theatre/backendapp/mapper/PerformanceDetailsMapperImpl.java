package red.theatre.backendapp.mapper;

import org.springframework.stereotype.Service;
import red.theatre.backendapp.dto.details.PerformanceDetailsCreateDTO;
import red.theatre.backendapp.model.PerformanceDetails;
@Service
public class PerformanceDetailsMapperImpl implements PerformanceDetailsMapper {
    @Override
    public PerformanceDetails fromCreateDTO(PerformanceDetailsCreateDTO dto) {
        PerformanceDetails details = new PerformanceDetails();
        details.setDescription(dto.getDescription());
        details.setName(dto.getName());
        details.setPictureUrl(dto.getPictureUrl());
        return details;
    }
}
