package red.theatre.backendapp.service;

import org.springframework.stereotype.Service;
import red.theatre.backendapp.dto.theatre.TheatreCreateDTO;
import red.theatre.backendapp.model.Theatre;

@Service
public class TheatreService {
    public void createTheatre(TheatreCreateDTO theatreCreateDTO) {
        Theatre theatre = new Theatre();
    }
}
