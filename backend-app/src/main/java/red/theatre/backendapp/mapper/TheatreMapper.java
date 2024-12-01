package red.theatre.backendapp.mapper;

import org.mapstruct.Mapper;
import red.theatre.backendapp.config.MapStructConfig;
import red.theatre.backendapp.dto.theatre.TheatreCreateDTO;
import red.theatre.backendapp.model.Theatre;
@Mapper(config = MapStructConfig.class)
public interface TheatreMapper {
    Theatre fromCreateDTO(TheatreCreateDTO theatreCreateDTO);
}
