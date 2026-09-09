package LogSentinel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LogNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleLogNotFound(
            LogNotFoundException exception) {

        return Map.of(
                "error", "Log Not Found",
                "message", exception.getMessage()
        );
    }

    @ExceptionHandler(IncidentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleIncidentNotFound(
            IncidentNotFoundException exception) {

        return Map.of(
                "error", "Incident Not Found",
                "message", exception.getMessage()
        );
    }

    @ExceptionHandler(TicketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleTicketNotFound(
            TicketNotFoundException exception) {

        return Map.of(
                "error", "Ticket Not Found",
                "message", exception.getMessage()
        );
    }

    @ExceptionHandler(InvalidLogException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidLog(
            InvalidLogException exception) {

        return Map.of(
                "error", "Invalid Log",
                "message", exception.getMessage()
        );
    }
    @ExceptionHandler(DuplicateLogException.class)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> handleDuplicateLog(
            DuplicateLogException exception) {

        return Map.of(
                "duplicate", true,
                "message", exception.getMessage()
        );
    }
}