package LogSentinel.service;

import LogSentinel.dto.ChatbotRequest;
import LogSentinel.dto.ChatbotResponse;
import LogSentinel.entity.Incident;
import LogSentinel.entity.Log;
import LogSentinel.exception.IncidentNotFoundException;
import LogSentinel.repository.IncidentRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatbotService {

    private final IncidentRepository incidentRepository;
    private final GeminiAiService geminiAiService;

    public ChatbotService(
            IncidentRepository incidentRepository,
            GeminiAiService geminiAiService) {

        this.incidentRepository = incidentRepository;
        this.geminiAiService = geminiAiService;
    }

    public ChatbotResponse chat(ChatbotRequest request) {

        Incident incident = incidentRepository.findById(
                request.getIncidentId()
        ).orElseThrow(() ->
                new IncidentNotFoundException(
                        "Incident not found with id: "
                                + request.getIncidentId()
                ));

        Log relatedLog = incident.getRelatedLog();

        String response = geminiAiService.chatAboutIncident(
                incident,
                relatedLog,
                request.getMessage()
        );

        return new ChatbotResponse(response);
    }
}
