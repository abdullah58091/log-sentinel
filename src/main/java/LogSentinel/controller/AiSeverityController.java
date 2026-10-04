package LogSentinel.controller;

import LogSentinel.service.AiSeverityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiSeverityController {

    private final AiSeverityService aiSeverityService;

    public AiSeverityController(AiSeverityService aiSeverityService) {
        this.aiSeverityService = aiSeverityService;
    }

    @PostMapping("/severity")
    public ResponseEntity<SeverityResponse> suggestSeverity(
            @Valid @RequestBody SeverityRequest request) {

        String severity =
                aiSeverityService.suggestSeverity(request.logMessage());

        return ResponseEntity.ok(new SeverityResponse(severity));
    }

    public record SeverityRequest(
            @NotBlank(message = "Log message is required")
            String logMessage
    ) {
    }

    public record SeverityResponse(
            String severity
    ) {
    }
}