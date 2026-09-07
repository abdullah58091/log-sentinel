package LogSentinel.processor;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TimestampHandler {

    private static final Pattern TIMESTAMP_PATTERN = Pattern.compile(
            "^\\s*(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2})\\b"
    );

    private static final DateTimeFormatter TIMESTAMP_FORMATTER =
            DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public LocalDateTime extract(String rawLog) {

        if (rawLog == null || rawLog.isBlank()) {
            throw new IllegalArgumentException("Log cannot be empty");
        }

        Matcher matcher = TIMESTAMP_PATTERN.matcher(rawLog);

        if (!matcher.find()) {
            return LocalDateTime.now();
        }

        String timestampText = matcher.group(1);

        try {
            return LocalDateTime.parse(
                    timestampText,
                    TIMESTAMP_FORMATTER
            );
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(
                    "Invalid timestamp format"
            );
        }
    }
}
