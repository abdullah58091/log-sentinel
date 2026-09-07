package LogSentinel.processor;

import LogSentinel.entity.LogLevel;
import LogSentinel.repository.LogRepository;
import org.springframework.stereotype.Component;

@Component
public class DuplicateLogDetector {

    private final LogRepository logRepository;

    public DuplicateLogDetector(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public boolean isDuplicate(
            LogLevel level,
            String message,
            String source
    ) {

        if (level == null) {
            throw new IllegalArgumentException("Log level cannot be null");
        }

        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Log message cannot be empty");
        }

        if (source == null || source.isBlank()) {
            throw new IllegalArgumentException("Log source cannot be empty");
        }

        return logRepository.existsByLevelAndMessageAndSource(
                level,
                message.trim(),
                source.trim()
        );
    }
}
