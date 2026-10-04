package LogSentinel.entity;

import LogSentinel.enums.IncidentStatus;
import LogSentinel.enums.Severity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidentStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "related_log_id",
            nullable = false,
            unique = true
    )
    private Log relatedLog;

    public Incident() {
    }

    public Incident(
            String title,
            String description,
            Severity severity,
            IncidentStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Log relatedLog) {

        this.title = title;
        this.description = description;
        this.severity = severity;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.relatedLog = relatedLog;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public void setStatus(IncidentStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Log getRelatedLog() {
        return relatedLog;
    }

    public void setRelatedLog(Log relatedLog) {
        this.relatedLog = relatedLog;
    }
}
