package LogSentinel.processor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SourceDetectorTest {

    private final SourceDetector detector = new SourceDetector();

    @Test
    void shouldDetectSourceFromLog() {

        String result =
                detector.detect(
                        "ERROR [database-service] Database connection failed"
                );

        assertEquals("database-service", result);
    }

    @Test
    void shouldDetectSourceWithTimestamp() {

        String result =
                detector.detect(
                        "2026-09-07T09:30:00 ERROR [payment-service] Payment failed"
                );

        assertEquals("payment-service", result);
    }

    @Test
    void shouldReturnUnknownWhenSourceIsMissing() {

        String result =
                detector.detect(
                        "ERROR: Database connection failed"
                );

        assertEquals("UNKNOWN", result);
    }

    @Test
    void shouldRejectEmptyLog() {

        assertThrows(
                IllegalArgumentException.class,
                () -> detector.detect("")
        );
    }

    @Test
    void shouldRejectNullLog() {

        assertThrows(
                IllegalArgumentException.class,
                () -> detector.detect(null)
        );
    }
}
