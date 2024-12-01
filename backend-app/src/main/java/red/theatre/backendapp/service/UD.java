package red.theatre.backendapp.service;

import red.theatre.backendapp.defines.UseRoles;
import red.theatre.backendapp.dto.user.UserDetails;
import red.theatre.backendapp.exception.AuthForbiddenException;
import red.theatre.backendapp.exception.AuthUnauthorizedException;

public class UD {

    public static void validateClient(UserDetails userDetails) {
        if (userDetails == null) {
            throw unauthorizedAccess();
        }
        String role = userDetails.getRole();
        if (role.equals(UseRoles.GUEST)) {
            throw unauthorizedAccess();
        }
        if (role.equals(UseRoles.CLIENT)) {
            return;
        }
        throw new AuthForbiddenException("Заборонено здійснювати операцію");
    }

    public static void validateNotGuest(UserDetails userDetails) {
        boolean error = userDetails == null;
        if (!error) {
            String role = userDetails.getRole();
            if (role.equals(UseRoles.GUEST)) {
                error = true;
            }
        }
        if (error) {
            throw unauthorizedAccess();
        }
    }

    private static AuthUnauthorizedException unauthorizedAccess() {
        return new AuthUnauthorizedException("Користувач не авторизований");
    }
}
