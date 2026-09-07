package LogSentinel.dto;

import LogSentinel.entity.LogLevel;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProcessedLogResponseTest {

    @Test
    void shouldCreateProcessedLogResponse() {

        LocalDateTime timestamp =
                LocalDateTime.of(2026, 9, 7, 9, 30, 0);

        ProcessedLogResponse response =
                new ProcessedLogResponse(
                        LogLevel.ERROR,
                        "Database connection failed",
                        "database-service",
                        timestamp,
                        true,
                        false
                );

        assertEquals(LogLevel.ERROR, response.getLevel());
        assertEquals(
                "Database connection failed",
                response.getMessage()
        );
        assertEquals(
                "database-service",
                response.getSource()
        );
        assertEquals(timestamp, response.getTimestamp());
        assertTrue(response.isAbnormal());
        assertFalse(response.isDuplicate());
    }
}
