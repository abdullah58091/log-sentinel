package LogSentinel.processor;

import LogSentinel.entity.LogLevel;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LogLevelDetector {

    private static final Pattern LEVEL_PATTERN = Pattern.compile(
            "\\b(TRACE|DEBUG|INFO|WARN|ERROR|FATAL)\\b",
            Pattern.CASE_INSENSITIVE
    );

    public LogLevel detect(String rawLog) {

        if (rawLog == null || rawLog.isBlank()) {
            throw new IllegalArgumentException("Log cannot be empty");
        }

        Matcher matcher = LEVEL_PATTERN.matcher(rawLog);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Log level could not be detected");
        }

        return LogLevel.valueOf(matcher.group(1).toUpperCase());
    }
}
