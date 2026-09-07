package LogSentinel.processor;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MessageExtractor {

    private static final Pattern MESSAGE_PATTERN = Pattern.compile(
            "^\\s*(?:(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2})\\s+)?"
                    + "\\[?(TRACE|DEBUG|INFO|WARN|ERROR|FATAL)\\]?\\s*"
                    + "(?:\\[([^\\]]+)\\]\\s*)?"
                    + "[:\\-]?\\s*(.+?)\\s*$",
            Pattern.CASE_INSENSITIVE
    );

    public String extract(String rawLog) {

        if (rawLog == null || rawLog.isBlank()) {
            throw new IllegalArgumentException("Log cannot be empty");
        }

        Matcher matcher = MESSAGE_PATTERN.matcher(rawLog);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid log format");
        }

        String message = matcher.group(4);

        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Log message cannot be empty");
        }

        return message.trim();
    }
}
