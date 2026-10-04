package LogSentinel.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

@Service
public class AiSeverityService {

    private final Client geminiClient;

    public AiSeverityService(Client geminiClient) {
        this.geminiClient = geminiClient;
    }

    public String suggestSeverity(String logMessage) {

        String prompt = """
                Analyze the following application log and suggest its severity.

                Choose exactly one severity:
                LOW
                MEDIUM
                HIGH
                CRITICAL

                Return only the severity name.

                Log:
                %s
                """.formatted(logMessage);

        GenerateContentResponse response = geminiClient.models.generateContent(
                "gemini-2.5-flash",
                prompt,
                null
        );

        return response.text().trim();
    }
}