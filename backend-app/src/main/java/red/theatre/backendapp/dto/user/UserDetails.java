package red.theatre.backendapp.dto.user;

import lombok.Data;
import red.theatre.backendapp.defines.UseRoles;

@Data
public class UserDetails {
    private Long id;
    private String login;
    private String role;

    public static UserDetails guest() {
        UserDetails userDetails = new UserDetails();
        userDetails.setLogin(null);
        userDetails.setId(null);
        userDetails.setRole(UseRoles.GUEST);
        return userDetails;
    }
}
