package LogSentinel.service;

import LogSentinel.dto.ProcessedLogResponse;
import LogSentinel.entity.Log;
import LogSentinel.entity.LogLevel;
import LogSentinel.enums.Severity;
import LogSentinel.exception.InvalidLogException;
import LogSentinel.parser.LogParser;
import LogSentinel.processor.AbnormalEventDetector;
import LogSentinel.processor.DuplicateLogDetector;
import LogSentinel.processor.LogLevelDetector;
import LogSentinel.processor.MessageExtractor;
import LogSentinel.processor.SourceDetector;
import LogSentinel.processor.TimestampHandler;
import LogSentinel.repository.LogRepository;
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
    private final SeverityService severityService;
    private final LogRepository logRepository;
    private final IncidentService incidentService;

    public LogProcessingService(
            LogParser logParser,
            LogLevelDetector logLevelDetector,
            MessageExtractor messageExtractor,
            SourceDetector sourceDetector,
            TimestampHandler timestampHandler,
            AbnormalEventDetector abnormalEventDetector,
            DuplicateLogDetector duplicateLogDetector,
            SeverityService severityService,
            LogRepository logRepository,
            IncidentService incidentService
    ) {
        this.logParser = logParser;
        this.logLevelDetector = logLevelDetector;
        this.messageExtractor = messageExtractor;
        this.sourceDetector = sourceDetector;
        this.timestampHandler = timestampHandler;
        this.abnormalEventDetector = abnormalEventDetector;
        this.duplicateLogDetector = duplicateLogDetector;
        this.severityService = severityService;
        this.logRepository = logRepository;
        this.incidentService = incidentService;
    }

    public ProcessedLogResponse process(String rawLog) {

        try {

            if (rawLog == null || rawLog.isBlank()) {
                throw new IllegalArgumentException("Log cannot be empty");
            }

            Log parsedLog = logParser.parse(rawLog);

            LogLevel level =
                    logLevelDetector.detect(rawLog);

            String message =
                    messageExtractor.extract(rawLog);

            Severity severity =
                    severityService.determineSeverity(
                            level,
                            message
                    );

            String source =
                    sourceDetector.detect(rawLog);

            LocalDateTime timestamp =
                    timestampHandler.extract(rawLog);

            parsedLog.setLevel(level);
            parsedLog.setMessage(message);
            parsedLog.setSource(source);
            parsedLog.setTimestamp(timestamp);

            boolean abnormal =
                    abnormalEventDetector.isAbnormal(level);

            boolean duplicate =
                    duplicateLogDetector.isDuplicate(
                            level,
                            message,
                            source
                    );

            Log savedLog = logRepository.save(parsedLog);

            if (severity == Severity.HIGH ||
                    severity == Severity.CRITICAL) {

                incidentService.createAutomaticIncident(
                        savedLog,
                        severity
                );
            }

            return new ProcessedLogResponse(
                    level,
                    severity,
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