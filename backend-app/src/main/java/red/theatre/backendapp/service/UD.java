package red.theatre.backendapp.service;

import red.theatre.backendapp.defines.UseRoles;
import red.theatre.backendapp.dto.user.UserDetails;
import red.theatre.backendapp.exception.AuthForbiddenException;
import red.theatre.backendapp.exception.AuthUnauthorizedException;

public class UD {

    public static void validateClient(UserDetails userDetails) {
        String role = userDetails.getRole();
        if (role.equals(UseRoles.GUEST)) {
            throw new AuthUnauthorizedException("Користувач не авторизований");
        }
        if (role.equals(UseRoles.CLIENT)) {
            return;
        }
        throw new AuthForbiddenException("Заборонено здійснювати операцію");
    }
}
