package red.theatre.backendapp.dto.figure;
import lombok.Data;

@Data
public class FigureCreateDTO {
    private Long id;
    private String name;
    private String role;
}
