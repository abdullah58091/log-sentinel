package LogSentinel.dto;

import jakarta.validation.constraints.NotBlank;

public class RawLogRequest {

    @NotBlank(message = "Raw log cannot be empty")
    private String rawLog;

    public RawLogRequest() {
    }

    public String getRawLog() {
        return rawLog;
    }

    public void setRawLog(String rawLog) {
        this.rawLog = rawLog;
    }
}
