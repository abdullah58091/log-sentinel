package LogSentinel.exception;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler =
            new GlobalExceptionHandler();

    @Test
    void shouldHandleInvalidLogException() {

        InvalidLogException exception =
                new InvalidLogException("Invalid log format");

        Map<String, String> response =
                handler.handleInvalidLog(exception);

        assertEquals("Invalid Log", response.get("error"));
        assertEquals("Invalid log format", response.get("message"));
    }
}