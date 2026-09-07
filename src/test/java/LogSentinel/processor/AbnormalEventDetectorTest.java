package LogSentinel.processor;

import LogSentinel.entity.LogLevel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbnormalEventDetectorTest {

    private final AbnormalEventDetector detector =
            new AbnormalEventDetector();

    @Test
    void shouldDetectWarnAsAbnormal() {

        boolean result =
                detector.isAbnormal(LogLevel.WARN);

        assertTrue(result);
    }

    @Test
    void shouldDetectErrorAsAbnormal() {

        boolean result =
                detector.isAbnormal(LogLevel.ERROR);

        assertTrue(result);
    }

    @Test
    void shouldDetectFatalAsAbnormal() {

        boolean result =
                detector.isAbnormal(LogLevel.FATAL);

        assertTrue(result);
    }

    @Test
    void shouldDetectInfoAsNormal() {

        boolean result =
                detector.isAbnormal(LogLevel.INFO);

        assertFalse(result);
    }

    @Test
    void shouldDetectDebugAsNormal() {

        boolean result =
                detector.isAbnormal(LogLevel.DEBUG);

        assertFalse(result);
    }

    @Test
    void shouldDetectTraceAsNormal() {

        boolean result =
                detector.isAbnormal(LogLevel.TRACE);

        assertFalse(result);
    }

    @Test
    void shouldRejectNullLogLevel() {

        assertThrows(
                IllegalArgumentException.class,
                () -> detector.isAbnormal(null)
        );
    }
}
