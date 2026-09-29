package LogSentinel.dto;

import LogSentinel.entity.LogLevel;
import LogSentinel.enums.Severity;

import java.time.LocalDateTime;

public class ProcessedLogResponse {

    private LogLevel level;
    private Severity severity;
    private String message;
    private String source;
    private LocalDateTime timestamp;
    private boolean abnormal;
    private boolean duplicate;

    public ProcessedLogResponse() {
    }

    public ProcessedLogResponse(
            LogLevel level,
            Severity severity,
            String message,
            String source,
            LocalDateTime timestamp,
            boolean abnormal,
            boolean duplicate
    ) {
        this.level = level;
        this.severity = severity;
        this.message = message;
        this.source = source;
        this.timestamp = timestamp;
        this.abnormal = abnormal;
        this.duplicate = duplicate;
    }

    public LogLevel getLevel() {
        return level;
    }

    public Severity getSeverity() {
        return severity;
    }

    public String getMessage() {
        return message;
    }

    public String getSource() {
        return source;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isAbnormal() {
        return abnormal;
    }

    public boolean isDuplicate() {
        return duplicate;
    }
}