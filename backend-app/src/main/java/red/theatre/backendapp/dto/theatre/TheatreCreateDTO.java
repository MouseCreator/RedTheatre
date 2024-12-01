package red.theatre.backendapp.dto.theatre;

import lombok.Data;

@Data
public class TheatreCreateDTO {
    private String name;
    private String description;
    private String address;
    private Integer numSeats;
}
