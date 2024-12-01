package red.theatre.backendapp.exception;

public class AuthUnauthorizedException extends RuntimeException {
    public AuthUnauthorizedException() {
    }

    public AuthUnauthorizedException(String message) {
        super(message);
    }
}
