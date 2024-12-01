package red.theatre.backendapp.mapper;

import org.mapstruct.Mapper;
import red.theatre.backendapp.config.MapStructConfig;
import red.theatre.backendapp.dto.figure.FigureCreateDTO;
import red.theatre.backendapp.model.Figure;

@Mapper(config = MapStructConfig.class)
public interface FigureMapper {
    Figure fromCreateDTO(FigureCreateDTO createDTO);
}
