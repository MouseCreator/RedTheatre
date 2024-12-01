package red.theatre.backendapp.mapper;

import org.springframework.stereotype.Service;
import red.theatre.backendapp.dto.figure.FigureCreateDTO;
import red.theatre.backendapp.model.Figure;
@Service
public class FigureMapperImpl implements FigureMapper {
    @Override
    public Figure fromCreateDTO(FigureCreateDTO createDTO) {
        Figure figure = new Figure();
        figure.setName(createDTO.getName());
        figure.setRole(createDTO.getRole());
        return figure;
    }
}
