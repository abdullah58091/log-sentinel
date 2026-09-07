package LogSentinel.processor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageExtractorTest {

    private final MessageExtractor extractor = new MessageExtractor();

    @Test
    void shouldExtractMessageFromSimpleLog() {

        String result =
                extractor.extract("ERROR: Database connection failed");

        assertEquals("Database connection failed", result);
    }

    @Test
    void shouldExtractMessageFromLogWithTimestampAndSource() {

        String result =
                extractor.extract(
                        "2026-09-07T09:30:00 ERROR [database-service] Database connection failed"
                );

        assertEquals("Database connection failed", result);
    }

    @Test
    void shouldExtractMessageFromWarnLog() {

        String result =
                extractor.extract("WARN: Response time is high");

        assertEquals("Response time is high", result);
    }

    @Test
    void shouldRejectEmptyLog() {

        assertThrows(
                IllegalArgumentException.class,
                () -> extractor.extract("")
        );
    }

    @Test
    void shouldRejectInvalidLogFormat() {

        assertThrows(
                IllegalArgumentException.class,
                () -> extractor.extract("This is not a valid log")
        );
    }
}
