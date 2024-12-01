package red.theatre.backendapp.dto.user;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String login;
    private String role;
}
