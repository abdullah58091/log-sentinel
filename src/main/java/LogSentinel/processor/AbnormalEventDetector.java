package LogSentinel.processor;

import LogSentinel.entity.LogLevel;
import org.springframework.stereotype.Component;

@Component
public class AbnormalEventDetector {

    public boolean isAbnormal(LogLevel level) {

        if (level == null) {
            throw new IllegalArgumentException("Log level cannot be null");
        }

        return level == LogLevel.WARN
                || level == LogLevel.ERROR
                || level == LogLevel.FATAL;
    }
}
