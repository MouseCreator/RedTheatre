package red.theatre.backendapp.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import red.theatre.backendapp.dto.performance.FullPerformanceResponseDTO;
import red.theatre.backendapp.dto.performance.SimplePerformanceResponseDTO;
import red.theatre.backendapp.dto.seat.SeatResponseDTO;
import red.theatre.backendapp.model.*;
import red.theatre.backendapp.service.Dates;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PerformanceMapperImpl implements PerformanceMapper {

    private final Dates dates;
    private final SeatMapper seatMapper;
    @Override
    public SimplePerformanceResponseDTO toSimpleResponse(Performance performance) {
        SimplePerformanceResponseDTO simple = new SimplePerformanceResponseDTO();

        simple.setId(performance.getId());
        String date = dates.dateToString(performance.getTimeDate());
        simple.setDate(date);
        simple.setPerformanceName(performance.getDetails().getName());
        simple.setTheatreName(performance.getTheatre().getName());

        return simple;
    }

    @Override
    public FullPerformanceResponseDTO toFullResponse(Performance performance,
                                                     List<Figure> actors,
                                                     List<Seat> seats) {
        FullPerformanceResponseDTO full = new FullPerformanceResponseDTO();

        full.setId(performance.getId());
        String date = dates.dateToString(performance.getTimeDate());
        full.setDate(date);
        full.setPerformanceName(performance.getDetails().getName());
        full.setTheatreName(performance.getTheatre().getName());
        List<String> names = actors.stream().map(Figure::getName).toList();
        full.setFigures(names);
        List<SeatResponseDTO> seatsResponse = seats.stream().map(seatMapper::toResponse).toList();
        full.setSeats(seatsResponse);

        return full;
    }
}
