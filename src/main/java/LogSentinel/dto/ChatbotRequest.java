package LogSentinel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChatbotRequest {

    @NotNull(message = "Incident ID is required")
    private Long incidentId;

    @NotBlank(message = "Message is required")
    private String message;

    public ChatbotRequest() {
    }

    public Long getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(Long incidentId) {
        this.incidentId = incidentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
