package LogSentinel.service;

import LogSentinel.entity.Incident;
import LogSentinel.repository.IncidentRepository;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

@Service
public class AiIncidentReportService {

    private final Client geminiClient;
    private final IncidentRepository incidentRepository;

    public AiIncidentReportService(
            Client geminiClient,
            IncidentRepository incidentRepository) {
        this.geminiClient = geminiClient;
        this.incidentRepository = incidentRepository;
    }

    public String generateIncidentReport(Long incidentId) {

        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() ->
                        new RuntimeException("Incident not found with id: " + incidentId));

        String incidentDetails = """
                Incident ID: %s
                Title: %s
                Description: %s
                Severity: %s
                Status: %s
                Created At: %s
                Updated At: %s
                """.formatted(
                incident.getId(),
                incident.getTitle(),
                incident.getDescription(),
                incident.getSeverity(),
                incident.getStatus(),
                incident.getCreatedAt(),
                incident.getUpdatedAt()
        );

        String prompt = """
                Generate a professional incident report based ONLY on the provided incident details.

                IMPORTANT:
                - Do not invent dates, times, incident IDs, systems, root causes, downtime,
                  metrics, or other facts.
                - If information is not available, clearly state that it is not available.
                - Base the report only on the provided data.

                Include:
                1. Incident Summary
                2. Severity Assessment
                3. Root Cause Analysis
                4. Impact
                5. Recommended Actions
                6. Preventive Measures

                Incident Details:
                %s
                """.formatted(incidentDetails);

        GenerateContentResponse response = geminiClient.models.generateContent(
                "gemini-2.5-flash",
                prompt,
                null
        );

        return response.text();
    }
}