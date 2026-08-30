package LogSentinel.dto;

import LogSentinel.entity.LogLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateLogRequest {

    @NotNull(message = "Log level is required")
    private LogLevel level;

    @NotBlank(message = "Log message is required")
    @Size(max = 5000, message = "Log message must not exceed 5000 characters")
    private String message;

    @NotBlank(message = "Log source is required")
    @Size(max = 100, message = "Log source must not exceed 100 characters")
    private String source;

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}