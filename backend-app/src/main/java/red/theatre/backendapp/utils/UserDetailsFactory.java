package red.theatre.backendapp.utils;

import org.springframework.stereotype.Service;
import red.theatre.backendapp.defines.UseRoles;
import red.theatre.backendapp.dto.user.UserDetails;

@Service
public class UserDetailsFactory {
    public UserDetails guest() {
        return UserDetails.guest();
    }
    public UserDetails client() {
        UserDetails userDetails = new UserDetails();
        userDetails.setRole(UseRoles.CLIENT);
        userDetails.setId(1L);
        userDetails.setLogin("Петро");
        return userDetails;
    }
    public UserDetails admin() {
        UserDetails userDetails = new UserDetails();
        userDetails.setRole(UseRoles.ADMIN);
        userDetails.setId(1L);
        userDetails.setLogin("Петро");
        return userDetails;
    }
}
