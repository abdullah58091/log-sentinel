package LogSentinel.controller;

import LogSentinel.service.AiIncidentReportService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiIncidentReportController {

    private final AiIncidentReportService aiIncidentReportService;

    public AiIncidentReportController(
            AiIncidentReportService aiIncidentReportService) {
        this.aiIncidentReportService = aiIncidentReportService;
    }

    @PostMapping("/incident-report")
    public ResponseEntity<IncidentReportResponse> generateIncidentReport(
            @Valid @RequestBody IncidentReportRequest request) {

        String report =
                aiIncidentReportService.generateIncidentReport(
                        request.incidentId());

        return ResponseEntity.ok(new IncidentReportResponse(report));
    }

    public record IncidentReportRequest(
            @NotNull(message = "Incident ID is required")
            Long incidentId
    ) {
    }

    public record IncidentReportResponse(
            String report
    ) {
    }
}