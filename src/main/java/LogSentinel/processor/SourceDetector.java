package LogSentinel.processor;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SourceDetector {

    private static final Pattern SOURCE_PATTERN = Pattern.compile(
            "\\[([^\\]]+)\\]"
    );

    public String detect(String rawLog) {

        if (rawLog == null || rawLog.isBlank()) {
            throw new IllegalArgumentException("Log cannot be empty");
        }

        Matcher matcher = SOURCE_PATTERN.matcher(rawLog);

        if (!matcher.find()) {
            return "UNKNOWN";
        }

        String source = matcher.group(1);

        if (source == null || source.isBlank()) {
            return "UNKNOWN";
        }

        return source.trim();
    }
}
