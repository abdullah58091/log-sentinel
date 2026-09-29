package LogSentinel.service;

import LogSentinel.entity.LogLevel;
import LogSentinel.enums.Severity;
import org.springframework.stereotype.Service;

@Service
public class SeverityService {

    public Severity determineSeverity(LogLevel level) {

        if (level == null) {
            throw new IllegalArgumentException("Log level cannot be null");
        }

        return switch (level) {

            case TRACE, DEBUG, INFO -> Severity.LOW;

            case WARN -> Severity.MEDIUM;

            case ERROR -> Severity.HIGH;

            case FATAL -> Severity.CRITICAL;
        };
    }

    public Severity determineSeverity(
            LogLevel level,
            String message
    ) {

        if (level == null) {
            throw new IllegalArgumentException("Log level cannot be null");
        }

        if (message == null || message.isBlank()) {
            return determineSeverity(level);
        }

        String normalizedMessage =
                message.toLowerCase();

        if (level == LogLevel.FATAL) {
            return Severity.CRITICAL;
        }

        if (normalizedMessage.contains("database completely unavailable")
                || normalizedMessage.contains("production database")
                || normalizedMessage.contains("system completely down")) {

            return Severity.CRITICAL;
        }

        if (level == LogLevel.ERROR) {
            return Severity.HIGH;
        }

        if (level == LogLevel.WARN) {
            return Severity.MEDIUM;
        }

        return Severity.LOW;
    }
}