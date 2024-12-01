package red.theatre.backendapp.handler;

import lombok.Data;

@Data
public class ErrorResponse {
    private boolean error;
    private String message;
    public static ErrorResponse of(String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(true);
        errorResponse.setMessage(message);
        return errorResponse;
    }
}
