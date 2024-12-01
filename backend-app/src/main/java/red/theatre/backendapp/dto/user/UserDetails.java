package red.theatre.backendapp.dto.user;

import lombok.Data;

@Data
public class UserDetails {
    private Long id;
    private String login;
    private String role;
}
