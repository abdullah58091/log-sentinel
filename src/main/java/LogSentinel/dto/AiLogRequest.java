package LogSentinel.dto;

import jakarta.validation.constraints.NotBlank;

public class AiLogRequest {

    @NotBlank(message = "Log message is required")
    private String logMessage;

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }
}