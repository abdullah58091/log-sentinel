package LogSentinel.parser;

import LogSentinel.entity.Log;
import LogSentinel.entity.LogLevel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LogParser {

    private static final Pattern LOG_PATTERN = Pattern.compile(
            "^\\s*(?:(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2})\\s+)?"
                    + "\\[?(TRACE|DEBUG|INFO|WARN|ERROR|FATAL)\\]?\\s*"
                    + "(?:\\[([^\\]]+)\\]\\s*)?"
                    + "[:\\-]?\\s*(.+?)\\s*$",
            Pattern.CASE_INSENSITIVE
    );

    public Log parse(String rawLog) {

        if (rawLog == null || rawLog.isBlank()) {
            throw new IllegalArgumentException("Log cannot be empty");
        }

        Matcher matcher = LOG_PATTERN.matcher(rawLog);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid log format");
        }

        String timestampText = matcher.group(1);
        String levelText = matcher.group(2);
        String sourceText = matcher.group(3);
        String messageText = matcher.group(4);

        Log log = new Log();

        log.setLevel(LogLevel.valueOf(levelText.toUpperCase()));
        log.setMessage(messageText.trim());

        if (sourceText == null || sourceText.isBlank()) {
            log.setSource("UNKNOWN");
        } else {
            log.setSource(sourceText.trim());
        }

        log.setTimestamp(parseTimestamp(timestampText));

        return log;
    }

    private LocalDateTime parseTimestamp(String timestampText) {

        if (timestampText == null || timestampText.isBlank()) {
            return LocalDateTime.now();
        }

        try {
            return LocalDateTime.parse(
                    timestampText,
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME
            );
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("Invalid timestamp format");
        }
    }
}
