package red.theatre.backendapp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import red.theatre.backendapp.defines.Defines;
import red.theatre.backendapp.defines.SeatStatuses;
import red.theatre.backendapp.dto.performance.FullPerformanceResponseDTO;
import red.theatre.backendapp.dto.performance.PerformanceCreateDTO;
import red.theatre.backendapp.dto.performance.SimplePerformanceResponseDTO;
import red.theatre.backendapp.exception.DataNotFoundException;
import red.theatre.backendapp.mapper.PerformanceMapper;
import red.theatre.backendapp.model.*;
import red.theatre.backendapp.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Schedule {
    private final PerformanceRepository performanceRepository;
    private final SeatRepository seatRepository;
    private final PerformanceMapper performanceMapper;
    private final TheatreRepository theatreRepository;
    private final PerformanceDetailsRepository detailsRepository;
    private final PerformanceFigureRepository performanceFigureRepository;
    private final FigureRepository figureRepository;
    private final Dates dates;
    public List<SimplePerformanceResponseDTO> getAllPerformances() {
        List<Performance> allPerformances = performanceRepository.findAllByOrderByTimeDateAsc();
        return allPerformances.stream()
                .map(performanceMapper::toSimpleResponse)
                .toList();
    }

    public FullPerformanceResponseDTO getPerformanceById(Long id) {
        Performance performance = performanceRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Неможливо знайти виставу за ідентифікатором: " + id));
        List<Figure> figures = performanceFigureRepository.findAllByPerformance(performance.getId());
        List<Seat> seats = seatRepository.findAllByPerformance(performance.getId());
        return performanceMapper.toFullResponse(performance, figures, seats);
    }
    @Transactional
    public FullPerformanceResponseDTO createPerformance(PerformanceCreateDTO performanceCreateDTO) {
        Long detailsId = performanceCreateDTO.getDetailsId();
        PerformanceDetails details = detailsRepository
                .findById(detailsId)
                .orElseThrow(() -> new DataNotFoundException("Неможливо знайти деталі вистави з ідентифікатором " + detailsId));
        Long theatreId = performanceCreateDTO.getTheatreId();
        Theatre theatre = theatreRepository
                .findById(theatreId)
                .orElseThrow(()-> new DataNotFoundException("Неможливо знайти театр з ідентифікатором " + theatreId));
        Performance saved = savePerformance(performanceCreateDTO, details, theatre);
        List<Figure> figureList = matchFigures(performanceCreateDTO, saved);
        List<Seat> seats = createSeats(theatre, saved);

        return performanceMapper.toFullResponse(saved, figureList, seats);
    }
    @Transactional
    protected Performance savePerformance(PerformanceCreateDTO performanceCreateDTO, PerformanceDetails details, Theatre theatre) {
        Performance performance = new Performance();

        performance.setDetails(details);
        performance.setTheatre(theatre);
        String date = performanceCreateDTO.getDate();
        LocalDateTime localDateTime = dates.stringToDate(date);
        performance.setTimeDate(localDateTime);
        Long directorId = performanceCreateDTO.getDirector();
        Figure director = figureRepository.findById(directorId)
                .orElseThrow(() -> new DataNotFoundException("Неможливо знайти режисера з ідентифікатором " + directorId));
        performance.setDirector(director);

        return performanceRepository.save(performance);
    }

    private List<Figure> matchFigures(PerformanceCreateDTO performanceCreateDTO, Performance performance) {
        List<Long> figures = performanceCreateDTO.getFigures();
        List<Figure> figureList = new ArrayList<>();
        for (Long figureId : figures) {
            Figure figure = figureRepository.findById(figureId)
                    .orElseThrow(()->new DataNotFoundException("Не існує театрального діяча з ідентифікатором: " + figureId));
            PerformanceFigure performanceFigure = new PerformanceFigure(performance, figure);
            performanceFigure.setId(new PFKey(performance.getId(), figureId));
            performanceFigureRepository.save(performanceFigure);
            figureList.add(figure);
        }
        return figureList;
    }
    @Transactional
    protected List<Seat> createSeats(Theatre theatre, Performance saved) {
        int numSeats = theatre.getNumSeats();
        List<Seat> seats = new ArrayList<>();
        for (int position = 1; position <= numSeats; position++) {
            Seat seat = new Seat();
            seat.setPerformance(saved);
            seat.setPosition(position);
            seat.setStatus(SeatStatuses.FREE);
            seat.setPrice(Defines.SEAT_PRICE);
            seats.add(seat);
        }
        return seatRepository.saveAll(seats);
    }
}
