package LogSentinel.dto;

import LogSentinel.entity.LogLevel;

import java.time.LocalDateTime;

public class LogResponse {

    private Long id;
    private LogLevel level;
    private String message;
    private String source;
    private LocalDateTime timestamp;

    public LogResponse() {
    }

    public LogResponse(Long id, LogLevel level, String message,
                       String source, LocalDateTime timestamp) {
        this.id = id;
        this.level = level;
        this.message = message;
        this.source = source;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
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
}