package red.theatre.backendapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import red.theatre.backendapp.dto.figure.FigureCreateDTO;
import red.theatre.backendapp.mapper.FigureMapper;
import red.theatre.backendapp.model.Figure;
import red.theatre.backendapp.repository.FigureRepository;

@Service
@RequiredArgsConstructor
public class FigureService {
    private final FigureRepository figureRepository;
    public final FigureMapper figureMapper;
    public void createFigure(FigureCreateDTO figureCreateDTO) {
        Figure figure = figureMapper.fromCreateDTO(figureCreateDTO);
        figureRepository.save(figure);
    }
}
