package LogSentinel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(LogNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleLogNotFound(
            LogNotFoundException exception) {

        logger.error("Log not found: {}", exception.getMessage());

        return Map.of(
                "error", "Log Not Found",
                "message", exception.getMessage()
        );
    }

    @ExceptionHandler(IncidentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleIncidentNotFound(
            IncidentNotFoundException exception) {

        logger.error("Incident not found: {}", exception.getMessage());

        return Map.of(
                "error", "Incident Not Found",
                "message", exception.getMessage()
        );
    }

    @ExceptionHandler(TicketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleTicketNotFound(
            TicketNotFoundException exception) {

        logger.error("Ticket not found: {}", exception.getMessage());

        return Map.of(
                "error", "Ticket Not Found",
                "message", exception.getMessage()
        );
    }

    @ExceptionHandler(InvalidLogException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidLog(
            InvalidLogException exception) {

        logger.error("Invalid log: {}", exception.getMessage());

        return Map.of(
                "error", "Invalid Log",
                "message", exception.getMessage()
        );
    }

    @ExceptionHandler(DuplicateLogException.class)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> handleDuplicateLog(
            DuplicateLogException exception) {

        logger.warn("Duplicate log detected: {}", exception.getMessage());

        return Map.of(
                "duplicate", true,
                "message", exception.getMessage()
        );
    }
}