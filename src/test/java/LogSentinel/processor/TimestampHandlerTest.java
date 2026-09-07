package LogSentinel.processor;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimestampHandlerTest {

    private final TimestampHandler handler = new TimestampHandler();

    @Test
    void shouldExtractTimestampFromLog() {

        LocalDateTime result =
                handler.extract(
                        "2026-09-07T09:30:00 ERROR [database-service] Database connection failed"
                );

        assertEquals(
                LocalDateTime.of(2026, 9, 7, 9, 30, 0),
                result
        );
    }

    @Test
    void shouldUseCurrentTimestampWhenTimestampIsMissing() {

        LocalDateTime before = LocalDateTime.now();

        LocalDateTime result =
                handler.extract(
                        "ERROR: Database connection failed"
                );

        LocalDateTime after = LocalDateTime.now();

        assertFalse(result.isBefore(before));
        assertFalse(result.isAfter(after));
    }

    @Test
    void shouldRejectEmptyLog() {

        assertThrows(
                IllegalArgumentException.class,
                () -> handler.extract("")
        );
    }

    @Test
    void shouldRejectNullLog() {

        assertThrows(
                IllegalArgumentException.class,
                () -> handler.extract(null)
        );
    }
}