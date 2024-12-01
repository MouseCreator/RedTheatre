package red.theatre.backendapp.mapper;

import red.theatre.backendapp.dto.seat.SeatResponseDTO;
import red.theatre.backendapp.model.Seat;
public interface SeatMapper {
    SeatResponseDTO toResponse(Seat seat);
}
