package LogSentinel.service;

import LogSentinel.dto.ProcessedLogResponse;
import LogSentinel.entity.Log;
import LogSentinel.entity.LogLevel;
import LogSentinel.exception.InvalidLogException;
import LogSentinel.parser.LogParser;
import LogSentinel.processor.AbnormalEventDetector;
import LogSentinel.processor.DuplicateLogDetector;
import LogSentinel.processor.LogLevelDetector;
import LogSentinel.processor.MessageExtractor;
import LogSentinel.processor.SourceDetector;
import LogSentinel.processor.TimestampHandler;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogProcessingServiceTest {

    private final LogParser logParser = mock(LogParser.class);

    private final LogLevelDetector logLevelDetector =
            mock(LogLevelDetector.class);

    private final MessageExtractor messageExtractor =
            mock(MessageExtractor.class);

    private final SourceDetector sourceDetector =
            mock(SourceDetector.class);

    private final TimestampHandler timestampHandler =
            mock(TimestampHandler.class);

    private final AbnormalEventDetector abnormalEventDetector =
            mock(AbnormalEventDetector.class);

    private final DuplicateLogDetector duplicateLogDetector =
            mock(DuplicateLogDetector.class);

    private final LogProcessingService service =
            new LogProcessingService(
                    logParser,
                    logLevelDetector,
                    messageExtractor,
                    sourceDetector,
                    timestampHandler,
                    abnormalEventDetector,
                    duplicateLogDetector
            );

    @Test
    void shouldProcessValidLog() {

        String rawLog =
                "2026-09-07T09:30:00 ERROR [database-service] Database connection failed";

        LocalDateTime timestamp =
                LocalDateTime.of(2026, 9, 7, 9, 30, 0);

        Log parsedLog = new Log();

        parsedLog.setLevel(LogLevel.ERROR);
        parsedLog.setMessage("Database connection failed");
        parsedLog.setSource("database-service");
        parsedLog.setTimestamp(timestamp);

        when(logParser.parse(rawLog))
                .thenReturn(parsedLog);

        when(logLevelDetector.detect(rawLog))
                .thenReturn(LogLevel.ERROR);

        when(messageExtractor.extract(rawLog))
                .thenReturn("Database connection failed");

        when(sourceDetector.detect(rawLog))
                .thenReturn("database-service");

        when(timestampHandler.extract(rawLog))
                .thenReturn(timestamp);

        when(abnormalEventDetector.isAbnormal(LogLevel.ERROR))
                .thenReturn(true);

        when(duplicateLogDetector.isDuplicate(
                LogLevel.ERROR,
                "Database connection failed",
                "database-service"
        )).thenReturn(false);

        ProcessedLogResponse result =
                service.process(rawLog);

        assertNotNull(result);

        assertEquals(
                LogLevel.ERROR,
                result.getLevel()
        );

        assertEquals(
                "Database connection failed",
                result.getMessage()
        );

        assertEquals(
                "database-service",
                result.getSource()
        );

        assertEquals(
                timestamp,
                result.getTimestamp()
        );

        assertTrue(
                result.isAbnormal()
        );

        assertFalse(
                result.isDuplicate()
        );
    }

    @Test
    void shouldRejectEmptyLog() {

        assertThrows(
                InvalidLogException.class,
                () -> service.process("")
        );
    }

    @Test
    void shouldRejectNullLog() {

        assertThrows(
                InvalidLogException.class,
                () -> service.process(null)
        );
    }
}
