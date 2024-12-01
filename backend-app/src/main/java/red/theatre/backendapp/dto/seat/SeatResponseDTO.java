package red.theatre.backendapp.dto.seat;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SeatResponseDTO {
    private Long id;
    private String status;
    private BigDecimal price;
    private Integer position;
}
