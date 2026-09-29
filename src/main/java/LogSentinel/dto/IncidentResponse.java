package LogSentinel.dto;

import LogSentinel.enums.IncidentStatus;
import LogSentinel.enums.Severity;

import java.time.LocalDateTime;

public class IncidentResponse {

    private Long id;
    private String title;
    private String description;
    private Severity severity;
    private IncidentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long relatedLogId;

    public IncidentResponse() {
    }

    public IncidentResponse(Long id,
                            String title,
                            String description,
                            Severity severity,
                            IncidentStatus status,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt,
                            Long relatedLogId) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.relatedLogId = relatedLogId;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Severity getSeverity() {
        return severity;
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getRelatedLogId() {
        return relatedLogId;
    }
}