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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogProcessingService {

    private final LogParser logParser;
    private final LogLevelDetector logLevelDetector;
    private final MessageExtractor messageExtractor;
    private final SourceDetector sourceDetector;
    private final TimestampHandler timestampHandler;
    private final AbnormalEventDetector abnormalEventDetector;
    private final DuplicateLogDetector duplicateLogDetector;

    public LogProcessingService(
            LogParser logParser,
            LogLevelDetector logLevelDetector,
            MessageExtractor messageExtractor,
            SourceDetector sourceDetector,
            TimestampHandler timestampHandler,
            AbnormalEventDetector abnormalEventDetector,
            DuplicateLogDetector duplicateLogDetector
    ) {
        this.logParser = logParser;
        this.logLevelDetector = logLevelDetector;
        this.messageExtractor = messageExtractor;
        this.sourceDetector = sourceDetector;
        this.timestampHandler = timestampHandler;
        this.abnormalEventDetector = abnormalEventDetector;
        this.duplicateLogDetector = duplicateLogDetector;
    }

    public ProcessedLogResponse process(String rawLog) {

        try {

            if (rawLog == null || rawLog.isBlank()) {
                throw new IllegalArgumentException("Log cannot be empty");
            }

            Log parsedLog = logParser.parse(rawLog);

            LogLevel level = logLevelDetector.detect(rawLog);

            String message = messageExtractor.extract(rawLog);

            String source = sourceDetector.detect(rawLog);

            LocalDateTime timestamp = timestampHandler.extract(rawLog);

            boolean abnormal =
                    abnormalEventDetector.isAbnormal(level);

            boolean duplicate =
                    duplicateLogDetector.isDuplicate(
                            level,
                            message,
                            source
                    );

            return new ProcessedLogResponse(
                    level,
                    message,
                    source,
                    timestamp,
                    abnormal,
                    duplicate
            );

        } catch (IllegalArgumentException exception) {

            throw new InvalidLogException(
                    exception.getMessage()
            );
        }
    }
}