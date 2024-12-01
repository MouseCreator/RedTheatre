package red.theatre.backendapp.dto.ticket;


import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class TicketResponseDTO {
    private Long id;
    private String ownerName;
    private String performanceName;
    private String theatreName;
    private String date;
    private String status;
    private List<Integer> seatNumbers;
    private BigDecimal totalPrice;
}
