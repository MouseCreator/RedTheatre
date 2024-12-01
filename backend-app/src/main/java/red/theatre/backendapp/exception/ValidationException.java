package red.theatre.backendapp.exception;

import java.io.Serial;

public class ValidationException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -8875915327633430600L;

    public ValidationException(String message) {
        super(message);
    }
}
