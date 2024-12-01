package red.theatre.backendapp.exception;

public class AuthForbiddenException extends RuntimeException{
    public AuthForbiddenException() {
    }

    public AuthForbiddenException(String message) {
        super(message);
    }
}
