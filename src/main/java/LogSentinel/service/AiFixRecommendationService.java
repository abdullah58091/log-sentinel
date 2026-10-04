package LogSentinel.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

@Service
public class AiFixRecommendationService {

    private final Client geminiClient;

    public AiFixRecommendationService(Client geminiClient) {
        this.geminiClient = geminiClient;
    }

    public String recommendFix(String logMessage) {

        String prompt = """
                Analyze the following application log.

                Provide practical and concise recommendations to fix the problem.

                Include:
                1. Immediate Fix
                2. Recommended Long-Term Fix

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