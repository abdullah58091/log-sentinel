package LogSentinel.dto;

import LogSentinel.entity.LogLevel;

import java.time.LocalDateTime;

public class ProcessedLogResponse {

    private LogLevel level;
    private String message;
    private String source;
    private LocalDateTime timestamp;
    private boolean abnormal;
    private boolean duplicate;

    public ProcessedLogResponse() {
    }

    public ProcessedLogResponse(
            LogLevel level,
            String message,
            String source,
            LocalDateTime timestamp,
            boolean abnormal,
            boolean duplicate
    ) {
        this.level = level;
        this.message = message;
        this.source = source;
        this.timestamp = timestamp;
        this.abnormal = abnormal;
        this.duplicate = duplicate;
    }

    public LogLevel getLevel() {
        return level;
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
