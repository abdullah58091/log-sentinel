package LogSentinel.exception;

public class DuplicateLogException extends RuntimeException {

    public DuplicateLogException(String message) {
        super(message);
    }
}