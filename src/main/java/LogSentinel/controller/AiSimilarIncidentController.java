package LogSentinel.controller;

import LogSentinel.service.AiSimilarIncidentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiSimilarIncidentController {

    private final AiSimilarIncidentService aiSimilarIncidentService;

    public AiSimilarIncidentController(
            AiSimilarIncidentService aiSimilarIncidentService) {
        this.aiSimilarIncidentService = aiSimilarIncidentService;
    }

    @PostMapping("/similar-incident")
    public ResponseEntity<SimilarIncidentResponse> findSimilarIncident(
            @Valid @RequestBody SimilarIncidentRequest request) {

        String result = aiSimilarIncidentService.findSimilarIncident(
                request.currentIncident()
        );

        return ResponseEntity.ok(new SimilarIncidentResponse(result));
    }

    public record SimilarIncidentRequest(
            @NotBlank(message = "Current incident is required")
            String currentIncident
    ) {
    }

    public record SimilarIncidentResponse(
            String result
    ) {
    }
}