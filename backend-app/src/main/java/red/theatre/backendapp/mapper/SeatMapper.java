package red.theatre.backendapp.mapper;

import org.mapstruct.Mapper;
import red.theatre.backendapp.config.MapStructConfig;
import red.theatre.backendapp.dto.seat.SeatResponseDTO;
import red.theatre.backendapp.model.Seat;
@Mapper(config = MapStructConfig.class)
public interface SeatMapper {
    SeatResponseDTO toResponse(Seat seat);
}
