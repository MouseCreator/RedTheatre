package red.theatre.backendapp.mapper;

import red.theatre.backendapp.dto.performance.FullPerformanceResponseDTO;
import red.theatre.backendapp.dto.performance.SimplePerformanceResponseDTO;
import red.theatre.backendapp.model.*;

import java.util.List;

public interface PerformanceMapper {
    SimplePerformanceResponseDTO toSimpleResponse(Performance performance);
    FullPerformanceResponseDTO toFullResponse(Performance performance, List<Figure> actors,  List<Seat> seats);

}
