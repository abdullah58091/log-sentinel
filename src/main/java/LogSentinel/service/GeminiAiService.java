package LogSentinel.service;

import LogSentinel.entity.Incident;
import LogSentinel.entity.Log;
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

    public String chatAboutIncident(
            Incident incident,
            Log relatedLog,
            String userQuestion) {

        String prompt = """
                You are an AI incident analysis assistant for a
                production application.

                Analyze the incident context below and answer the
                developer's question clearly and practically.

                Incident:
                ID: %s
                Title: %s
                Description: %s
                Severity: %s
                Status: %s

                Related Log:
                %s

                Developer Question:
                %s

                Give an accurate answer based on the provided
                incident and log context.
                If the information is not sufficient to determine
                the exact cause, clearly say that and provide
                reasonable things the developer should check.
                """.formatted(
                incident.getId(),
                incident.getTitle(),
                incident.getDescription(),
                incident.getSeverity(),
                incident.getStatus(),
                relatedLog.getMessage(),
                userQuestion
        );

        GenerateContentResponse response = geminiClient.models.generateContent(
                "gemini-2.5-flash",
                prompt,
                null
        );

        return response.text();
    }
}