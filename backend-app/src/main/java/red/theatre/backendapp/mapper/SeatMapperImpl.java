package red.theatre.backendapp.mapper;

import org.springframework.stereotype.Service;
import red.theatre.backendapp.dto.seat.SeatResponseDTO;
import red.theatre.backendapp.model.Seat;
@Service
public class SeatMapperImpl implements SeatMapper {
    @Override
    public SeatResponseDTO toResponse(Seat seat) {
        SeatResponseDTO dto = new SeatResponseDTO();
        dto.setId(seat.getId());
        dto.setPosition(seat.getPosition());
        dto.setPrice(seat.getPrice());
        dto.setStatus(seat.getStatus());
        return dto;
    }
}
