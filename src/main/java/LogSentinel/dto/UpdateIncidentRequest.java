package LogSentinel.dto;

import LogSentinel.enums.IncidentStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateIncidentRequest {

    @NotNull(message = "Status is required")
    private IncidentStatus status;

    public UpdateIncidentRequest() {
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public void setStatus(IncidentStatus status) {
        this.status = status;
    }
}
