package LogSentinel.processor;

import LogSentinel.entity.LogLevel;
import LogSentinel.repository.LogRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DuplicateLogDetectorTest {

    private final LogRepository logRepository =
            mock(LogRepository.class);

    private final DuplicateLogDetector detector =
            new DuplicateLogDetector(logRepository);

    @Test
    void shouldDetectDuplicateLog() {

        when(logRepository.existsByLevelAndMessageAndSource(
                LogLevel.ERROR,
                "Database connection failed",
                "database-service"
        )).thenReturn(true);

        boolean result =
                detector.isDuplicate(
                        LogLevel.ERROR,
                        "Database connection failed",
                        "database-service"
                );

        assertTrue(result);
    }

    @Test
    void shouldDetectNewLog() {

        when(logRepository.existsByLevelAndMessageAndSource(
                LogLevel.INFO,
                "Application started",
                "application-service"
        )).thenReturn(false);

        boolean result =
                detector.isDuplicate(
                        LogLevel.INFO,
                        "Application started",
                        "application-service"
                );

        assertFalse(result);
    }

    @Test
    void shouldRejectNullLogLevel() {

        assertThrows(
                IllegalArgumentException.class,
                () -> detector.isDuplicate(
                        null,
                        "Database connection failed",
                        "database-service"
                )
        );
    }

    @Test
    void shouldRejectEmptyMessage() {

        assertThrows(
                IllegalArgumentException.class,
                () -> detector.isDuplicate(
                        LogLevel.ERROR,
                        "",
                        "database-service"
                )
        );
    }

    @Test
    void shouldRejectEmptySource() {

        assertThrows(
                IllegalArgumentException.class,
                () -> detector.isDuplicate(
                        LogLevel.ERROR,
                        "Database connection failed",
                        ""
                )
        );
    }
}
