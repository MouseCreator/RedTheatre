package red.theatre.backendapp.exception;

import java.io.Serial;

public class DataNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -1810457903740608144L;

    public DataNotFoundException(String message) {
        super(message);
    }
}
