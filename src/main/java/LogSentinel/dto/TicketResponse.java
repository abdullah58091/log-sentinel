package LogSentinel.dto;

import LogSentinel.entity.TicketPriority;
import LogSentinel.entity.TicketStatus;

import java.time.LocalDateTime;

public class TicketResponse {

    private Long id;
    private String title;
    private String description;
    private TicketStatus status;
    private TicketPriority priority;
    private LocalDateTime createdAt;
    private Long incidentId;

    public TicketResponse() {
    }

    public TicketResponse(Long id,
                          String title,
                          String description,
                          TicketStatus status,
                          TicketPriority priority,
                          LocalDateTime createdAt,
                          Long incidentId) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.createdAt = createdAt;
        this.incidentId = incidentId;
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

    public TicketStatus getStatus() {
        return status;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getIncidentId() {
        return incidentId;
    }
}