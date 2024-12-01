package red.theatre.backendapp.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import red.theatre.backendapp.exception.DataNotFoundException;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(DataNotFoundException ex) {
        String message = ex.getMessage();
        log.error(message, ex);
        ErrorResponse errorResponse = ErrorResponse.of(message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
