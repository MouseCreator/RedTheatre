package red.theatre.backendapp.mapper;

import red.theatre.backendapp.dto.theatre.TheatreCreateDTO;
import red.theatre.backendapp.model.Theatre;
public interface TheatreMapper {
    Theatre fromCreateDTO(TheatreCreateDTO theatreCreateDTO);
}
