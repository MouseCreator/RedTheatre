package red.theatre.backendapp.exception;

import java.io.Serial;

public class DateException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1385604155584635623L;

    public DateException(String message) {
        super(message);
    }
}
