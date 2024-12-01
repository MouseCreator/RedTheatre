package red.theatre.backendapp.mapper;

import org.springframework.stereotype.Service;
import red.theatre.backendapp.dto.theatre.TheatreCreateDTO;
import red.theatre.backendapp.model.Theatre;
@Service
public class TheatreMapperImpl implements TheatreMapper {
    @Override
    public Theatre fromCreateDTO(TheatreCreateDTO dto) {
        Theatre theatre = new Theatre();
        theatre.setAddress(dto.getAddress());
        theatre.setName(dto.getName());
        theatre.setDescription(dto.getDescription());
        theatre.setNumSeats(dto.getNumSeats());
        return theatre;
    }
}
