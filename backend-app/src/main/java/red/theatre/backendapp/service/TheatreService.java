package red.theatre.backendapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import red.theatre.backendapp.dto.theatre.TheatreCreateDTO;
import red.theatre.backendapp.mapper.TheatreMapper;
import red.theatre.backendapp.model.Theatre;
import red.theatre.backendapp.repository.TheatreRepository;

@Service
@RequiredArgsConstructor
public class TheatreService {
    private final TheatreMapper theatreMapper;
    private final TheatreRepository theatreRepository;
    public void createTheatre(TheatreCreateDTO theatreCreateDTO) {
        Theatre theatre = theatreMapper.fromCreateDTO(theatreCreateDTO);
        theatreRepository.save(theatre);
    }
}
