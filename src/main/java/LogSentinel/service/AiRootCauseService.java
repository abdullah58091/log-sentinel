package LogSentinel.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

@Service
public class AiRootCauseService {

    private final Client geminiClient;

    public AiRootCauseService(Client geminiClient) {
        this.geminiClient = geminiClient;
    }

    public String analyzeRootCause(String logMessage) {

        String prompt = """
                Analyze the following application log and identify the most likely root cause.

                Provide:
                1. Most Likely Root Cause
                2. Supporting Evidence
                3. Confidence Level

                Log:
                %s
                """.formatted(logMessage);

        GenerateContentResponse response = geminiClient.models.generateContent(
                "gemini-2.5-flash",
                prompt,
                null
        );

        return response.text();
    }
}