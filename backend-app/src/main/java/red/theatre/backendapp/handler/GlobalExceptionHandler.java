package red.theatre.backendapp.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import red.theatre.backendapp.exception.*;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(DataNotFoundException ex) {
        String message = ex.getMessage();
        log.error(message, ex);
        ErrorResponse errorResponse = ErrorResponse.of(message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handle(ValidationException ex) {
        String message = ex.getMessage();
        log.error(message, ex);
        ErrorResponse errorResponse = ErrorResponse.of(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DateException.class)
    public ResponseEntity<ErrorResponse> handle(DateException ex) {
        String message = ex.getMessage();
        log.error(message, ex);
        ErrorResponse errorResponse = ErrorResponse.of(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AuthUnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handle(AuthUnauthorizedException ex) {
        String message = ex.getMessage();
        log.error(message, ex);
        ErrorResponse errorResponse = ErrorResponse.of(message);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(AuthForbiddenException.class)
    public ResponseEntity<ErrorResponse> handle(AuthForbiddenException ex) {
        String message = ex.getMessage();
        log.error(message, ex);
        ErrorResponse errorResponse = ErrorResponse.of(message);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handle(RuntimeException ex) {
        String message = ex.getMessage();
        log.error(message, ex);
        ErrorResponse errorResponse = ErrorResponse.of("Помилка сервера");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> handle(InternalServerException ex) {
        String message = ex.getMessage();
        log.error(message, ex);
        ErrorResponse errorResponse = ErrorResponse.of(message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
