package LogSentinel.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Document(indexName = "logs")
public class LogDocument {

    @Id
    private String id;

    private String level;
    private String severity;
    private String message;
    private String source;
    private LocalDateTime timestamp;
    private Boolean abnormal;
    private Boolean duplicate;

    public LogDocument() {
    }

    public LogDocument(
            String id,
            String level,
            String severity,
            String message,
            String source,
            LocalDateTime timestamp,
            Boolean abnormal,
            Boolean duplicate) {

        this.id = id;
        this.level = level;
        this.severity = severity;
        this.message = message;
        this.source = source;
        this.timestamp = timestamp;
        this.abnormal = abnormal;
        this.duplicate = duplicate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getAbnormal() {
        return abnormal;
    }

    public void setAbnormal(Boolean abnormal) {
        this.abnormal = abnormal;
    }

    public Boolean getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(Boolean duplicate) {
        this.duplicate = duplicate;
    }
}