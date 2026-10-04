package LogSentinel.service;

import LogSentinel.entity.Incident;
import LogSentinel.repository.IncidentRepository;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiSimilarIncidentService {

    private final Client geminiClient;
    private final IncidentRepository incidentRepository;

    public AiSimilarIncidentService(
            Client geminiClient,
            IncidentRepository incidentRepository) {
        this.geminiClient = geminiClient;
        this.incidentRepository = incidentRepository;
    }

    public String findSimilarIncident(String currentIncident) {

        List<Incident> incidents = incidentRepository.findAll();

        String existingIncidents = incidents.stream()
                .map(incident -> """
                        Incident #%s
                        Title: %s
                        Description: %s
                        Severity: %s
                        Status: %s
                        """.formatted(
                        incident.getId(),
                        incident.getTitle(),
                        incident.getDescription(),
                        incident.getSeverity(),
                        incident.getStatus()
                ))
                .reduce("", (a, b) -> a + "\n" + b);

        String prompt = """
                Compare the current incident with the existing incidents.

                Current Incident:
                %s

                Existing Incidents:
                %s

                Identify the most similar existing incident.

                IMPORTANT:
                - Compare only against the provided existing incidents.
                - Do not invent an incident.
                - Do not modify incident IDs or details.
                - If no incident is sufficiently similar, clearly state:
                  No sufficiently similar incident found.

                Provide:
                1. Similar Incident
                2. Reason for Similarity
                3. Confidence Level
                """.formatted(currentIncident, existingIncidents);

        GenerateContentResponse response = geminiClient.models.generateContent(
                "gemini-2.5-flash",
                prompt,
                null
        );

        return response.text();
    }
}