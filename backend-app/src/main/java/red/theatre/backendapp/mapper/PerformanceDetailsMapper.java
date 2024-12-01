package red.theatre.backendapp.mapper;

import org.mapstruct.Mapper;
import red.theatre.backendapp.config.MapStructConfig;
import red.theatre.backendapp.dto.details.PerformanceDetailsCreateDTO;
import red.theatre.backendapp.model.PerformanceDetails;

@Mapper(config = MapStructConfig.class)
public interface PerformanceDetailsMapper {
    PerformanceDetails fromCreateDTO(PerformanceDetailsCreateDTO createDTO);
}
