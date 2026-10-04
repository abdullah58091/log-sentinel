package LogSentinel.dto;

import LogSentinel.entity.Notification;

import java.time.LocalDateTime;

public class NotificationResponse {

    private Long id;
    private Long incidentId;
    private String type;
    private String message;
    private boolean read;
    private LocalDateTime createdAt;

    public NotificationResponse() {
    }

    public NotificationResponse(
            Long id,
            Long incidentId,
            String type,
            String message,
            boolean read,
            LocalDateTime createdAt) {

        this.id = id;
        this.incidentId = incidentId;
        this.type = type;
        this.message = message;
        this.read = read;
        this.createdAt = createdAt;
    }

    public static NotificationResponse fromEntity(
            Notification notification) {

        Long incidentId = notification.getIncident() != null
                ? notification.getIncident().getId()
                : null;

        return new NotificationResponse(
                notification.getId(),
                incidentId,
                notification.getType(),
                notification.getMessage(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getIncidentId() {
        return incidentId;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}