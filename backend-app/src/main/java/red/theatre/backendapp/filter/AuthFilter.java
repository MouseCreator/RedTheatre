package red.theatre.backendapp.filter;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import red.theatre.backendapp.dto.user.UserDetails;
import red.theatre.backendapp.dto.user.UserResponseDTO;
import red.theatre.backendapp.model.User;
import red.theatre.backendapp.service.UserService;

import java.io.IOException;
import java.util.Optional;

@Component
@Log4j2
public class AuthFilter  extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final UserService userService;
    public AuthFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(AUTHORIZATION_HEADER);

        if (header == null || header.isEmpty()) {
            UserDetails userDetails = mockUser();
            request.setAttribute("user", userDetails);
        } else {
            UserDetails userDetails = new UserDetails();
            Optional<User> byLogin = userService.findByLogin(header);
            if (byLogin.isEmpty()) {
                log.info("User is not registered: " + byLogin);
                userDetails = UserDetails.guest();
            } else {
                User user = byLogin.get();
                userDetails.setLogin(user.getLogin());
                userDetails.setId(user.getId());
                userDetails.setRole(user.getRole());
            }
            request.setAttribute("user", userDetails);
        }

        filterChain.doFilter(request, response);
    }

    private UserDetails mockUser() {
        UserResponseDTO userById = userService.getUserById(7L);
        UserDetails userDetails = new UserDetails();
        userDetails.setLogin(userById.getLogin());
        userDetails.setId(userById.getId());
        userDetails.setRole(userById.getRole());
        return userDetails;
    }
}
