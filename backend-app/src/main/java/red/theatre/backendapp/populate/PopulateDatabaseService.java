package red.theatre.backendapp.populate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import red.theatre.backendapp.defines.FigureRoles;
import red.theatre.backendapp.defines.SeatStatuses;
import red.theatre.backendapp.dto.book.BookingCreateDTO;
import red.theatre.backendapp.dto.details.PerformanceDetailsCreateDTO;
import red.theatre.backendapp.dto.figure.FigureCreateDTO;
import red.theatre.backendapp.dto.performance.PerformanceCreateDTO;
import red.theatre.backendapp.dto.performance.PerformancePopulateDTO;
import red.theatre.backendapp.dto.theatre.TheatreCreateDTO;
import red.theatre.backendapp.dto.ticket.TicketPopulateDTO;
import red.theatre.backendapp.dto.user.UserCreateDTO;
import red.theatre.backendapp.dto.user.UserDetails;
import red.theatre.backendapp.model.*;
import red.theatre.backendapp.repository.SeatRepository;
import red.theatre.backendapp.service.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PopulateDatabaseService {
    private final TheatreService theatreService;
    private final FigureService figureService;
    private final PerformanceDetailsService performanceDetailsService;
    private final Schedule scheduleService;
    private final UserService userService;
    private final SeatRepository seatRepository;
    private final Booking booking;
    private record JSONData(
            List<TheatreCreateDTO> theatres,
            List<PerformanceDetailsCreateDTO> performanceDetails,
            List<FigureCreateDTO> figures,
            List<PerformancePopulateDTO> schedule,
            List<UserCreateDTO> users,
            List<TicketPopulateDTO> tickets) {
    }
    private record ActiveData(
            List<Theatre> theatres,
            List<PerformanceDetails> performanceDetails,
            List<Figure> figures,
            List<Performance> schedule,
            List<User> users) {
        public static ActiveData init() {
            return new ActiveData(
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>());
        }
    }
    private JSONData readData() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File("src/main/resources/samples/data.json"));
        List<TheatreCreateDTO> theatres = objectMapper.convertValue(rootNode.get("theatres"), new TypeReference<>() {});
        List<PerformanceDetailsCreateDTO> performances = objectMapper.convertValue(rootNode.get("performance_details"), new TypeReference<>() {});
        List<FigureCreateDTO> figures = objectMapper.convertValue(rootNode.get("figures"), new TypeReference<>() {});
        List<PerformancePopulateDTO> schedule = objectMapper.convertValue(rootNode.get("schedule"), new TypeReference<>() {});
        List<UserCreateDTO> users = objectMapper.convertValue(rootNode.get("users"), new TypeReference<>() {});
        List<TicketPopulateDTO> tickets = objectMapper.convertValue(rootNode.get("tickets"), new TypeReference<>() {});
        return new JSONData(theatres, performances, figures, schedule, users, tickets);
    }


    @Transactional
    protected void populate() {
        JSONData data;
        try {
            data = readData();
        } catch (Exception e) {
            throw new RuntimeException("Неможливо завантажити дані", e);
        }
        ActiveData activeData = ActiveData.init();
        createTheatres(data, activeData);
        createPerformanceDetails(data, activeData);
        createFigures(data, activeData);
        createSchedule(data, activeData);
        createUsers(data, activeData);
        createTickets(data, activeData);
    }

    private void createTickets(JSONData data, ActiveData activeData) {
        List<TicketPopulateDTO> tickets = data.tickets;
        for (TicketPopulateDTO dto : tickets) {
            BookingCreateDTO bookingCreateDTO = new BookingCreateDTO();
            Long performanceId = dto.getPerformanceId();
            List<Seat> allSeats = seatRepository.findAllByPerformance(performanceId);
            List<Seat> list = new ArrayList<>(
                    allSeats.stream().filter(s -> s.getStatus().equals(SeatStatuses.FREE))
                    .toList());
            Collections.shuffle(list);
            int j = Math.min(list.size(), dto.getNumberSeats());
            List<Integer> seats = new ArrayList<>();
            for (int i = 0; i < j; i++) {
                seats.add(list.get(i).getPosition());
            }
            bookingCreateDTO.setPerformanceId(performanceId);
            bookingCreateDTO.setSeats(seats);
            User user = activeData.users.stream().filter(u -> u.getLogin().equals(dto.getUserLogin())).findFirst().orElseThrow();
            UserDetails userDetails = new UserDetails();
            userDetails.setId(user.getId());
            userDetails.setRole(user.getRole());
            userDetails.setLogin(user.getLogin());
            booking.bookSeats(userDetails, bookingCreateDTO);
        }
    }

    private void createUsers(JSONData data, ActiveData activeData) {
        for (UserCreateDTO userCreateDTO : data.users) {
            User user = userService.populate(userCreateDTO);
            activeData.users.add(user);
        }
    }

    private void createSchedule(JSONData data, ActiveData activeData) {
        List<PerformancePopulateDTO> schedule = data.schedule;
        List<Figure> actors = activeData.figures.stream().filter(f -> f.getRole().equals(FigureRoles.ACTOR)).toList();
        for (PerformancePopulateDTO dto : schedule) {
            PerformanceCreateDTO performance = new PerformanceCreateDTO();
            performance.setDate(dto.getDate());
            performance.setDirector(dto.getDirector());
            performance.setDetailsId(dto.getDetailsId());
            performance.setTheatreId(dto.getTheatreId());
            List<Figure> actorsLocal = new ArrayList<>(actors);
            Collections.shuffle(actorsLocal);
            List<Long> ids = new ArrayList<>();
            for (int i = 0; i < dto.getNumberFigures(); i++) {
                ids.add(actorsLocal.get(i).getId());
            }
            performance.setFigures(ids);
            scheduleService.createPerformance(performance);
        }
    }

    private void createPerformanceDetails(JSONData data, ActiveData activeData) {
        List<PerformanceDetailsCreateDTO> performanceDetails = data.performanceDetails;
        for (PerformanceDetailsCreateDTO dto : performanceDetails) {
            PerformanceDetails pd = performanceDetailsService.createPerformanceDetails(dto);
            activeData.performanceDetails.add(pd);
        }
    }

    private void createFigures(JSONData data, ActiveData activeData) {
        List<FigureCreateDTO> figureCreateDTOS = data.figures;
        for (FigureCreateDTO dto : figureCreateDTOS) {
            Figure figure = figureService.createFigure(dto);
            activeData.figures.add(figure);
        }
    }

    private void createTheatres(JSONData data, ActiveData activeData) {
        List<TheatreCreateDTO> theatres = data.theatres;
        for (TheatreCreateDTO theatreCreateDTO : theatres) {
            Theatre theatre = theatreService.createTheatre(theatreCreateDTO);
            activeData.theatres.add(theatre);
        }
    }

    @PostConstruct
    @Transactional
    public void run() {
        populate();
    }
}
