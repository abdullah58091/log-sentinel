package LogSentinel.parser;

import LogSentinel.entity.Log;
import LogSentinel.entity.LogLevel;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LogParserTest {

    private final LogParser logParser = new LogParser();

    @Test
    void shouldParseLogWithLevelAndMessage() {

        String rawLog = "ERROR: Database connection failed";

        Log result = logParser.parse(rawLog);

        assertEquals(LogLevel.ERROR, result.getLevel());
        assertEquals("Database connection failed", result.getMessage());
        assertEquals("UNKNOWN", result.getSource());
        assertNotNull(result.getTimestamp());
    }

    @Test
    void shouldParseLogWithTimestampSourceLevelAndMessage() {

        String rawLog =
                "2026-09-07T09:30:00 ERROR [database-service] Database connection failed";

        Log result = logParser.parse(rawLog);

        assertEquals(LogLevel.ERROR, result.getLevel());
        assertEquals("Database connection failed", result.getMessage());
        assertEquals("database-service", result.getSource());

        assertEquals(
                LocalDateTime.of(2026, 9, 7, 9, 30, 0),
                result.getTimestamp()
        );
    }

    @Test
    void shouldRejectEmptyLog() {

        assertThrows(
                IllegalArgumentException.class,
                () -> logParser.parse("")
        );
    }

    @Test
    void shouldRejectInvalidLogFormat() {

        assertThrows(
                IllegalArgumentException.class,
                () -> logParser.parse("This is not a valid log")
        );
    }
}
