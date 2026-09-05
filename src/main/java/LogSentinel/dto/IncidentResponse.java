package LogSentinel.dto;

import LogSentinel.entity.IncidentStatus;

import java.time.LocalDateTime;

public class IncidentResponse {

    private Long id;
    private String title;
    private String description;
    private IncidentStatus status;
    private LocalDateTime createdAt;

    public IncidentResponse() {
    }

    public IncidentResponse(Long id,
                            String title,
                            String description,
                            IncidentStatus status,
                            LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
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

    public IncidentStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}