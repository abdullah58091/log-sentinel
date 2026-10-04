package LogSentinel.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

@Service
public class GeminiAiService implements AiService {

    private final Client geminiClient;

    public GeminiAiService(Client geminiClient) {
        this.geminiClient = geminiClient;
    }

    @Override
    public String analyzeLog(String logMessage) {

        String prompt = """
                Analyze the following application log.

                Provide:
                1. Summary
                2. Possible Root Cause
                3. Recommended Fix

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