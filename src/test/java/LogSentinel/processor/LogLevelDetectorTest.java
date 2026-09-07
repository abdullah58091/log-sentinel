package LogSentinel.processor;

import LogSentinel.entity.LogLevel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogLevelDetectorTest {

    private final LogLevelDetector detector = new LogLevelDetector();

    @Test
    void shouldDetectErrorLevel() {

        LogLevel result =
                detector.detect("ERROR: Database connection failed");

        assertEquals(LogLevel.ERROR, result);
    }

    @Test
    void shouldDetectWarnLevel() {

        LogLevel result =
                detector.detect("WARN: Response time is high");

        assertEquals(LogLevel.WARN, result);
    }

    @Test
    void shouldDetectInfoLevel() {

        LogLevel result =
                detector.detect("INFO: Application started successfully");

        assertEquals(LogLevel.INFO, result);
    }

    @Test
    void shouldDetectDebugLevel() {

        LogLevel result =
                detector.detect("DEBUG: User request received");

        assertEquals(LogLevel.DEBUG, result);
    }

    @Test
    void shouldDetectFatalLevel() {

        LogLevel result =
                detector.detect("FATAL: Application crashed");

        assertEquals(LogLevel.FATAL, result);
    }

    @Test
    void shouldDetectTraceLevel() {

        LogLevel result =
                detector.detect("TRACE: Entering method");

        assertEquals(LogLevel.TRACE, result);
    }

    @Test
    void shouldRejectLogWithoutLevel() {

        assertThrows(
                IllegalArgumentException.class,
                () -> detector.detect("Database connection failed")
        );
    }

    @Test
    void shouldRejectEmptyLog() {

        assertThrows(
                IllegalArgumentException.class,
                () -> detector.detect("")
        );
    }
}
