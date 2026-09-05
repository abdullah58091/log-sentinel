package LogSentinel.exception;

public class IncidentNotFoundException extends RuntimeException {

    public IncidentNotFoundException(String message) {
        super(message);
    }
}