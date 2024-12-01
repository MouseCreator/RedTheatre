package red.theatre.backendapp.mapper;

import red.theatre.backendapp.dto.figure.FigureCreateDTO;
import red.theatre.backendapp.model.Figure;

public interface FigureMapper {
    Figure fromCreateDTO(FigureCreateDTO createDTO);
}
