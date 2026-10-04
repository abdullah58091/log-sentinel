package LogSentinel.controller;

import LogSentinel.service.AiRootCauseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiRootCauseController {

    private final AiRootCauseService aiRootCauseService;

    public AiRootCauseController(AiRootCauseService aiRootCauseService) {
        this.aiRootCauseService = aiRootCauseService;
    }

    @PostMapping("/root-cause")
    public ResponseEntity<RootCauseResponse> analyzeRootCause(
            @Valid @RequestBody RootCauseRequest request) {

        String rootCause =
                aiRootCauseService.analyzeRootCause(request.logMessage());

        return ResponseEntity.ok(new RootCauseResponse(rootCause));
    }

    public record RootCauseRequest(
            @NotBlank(message = "Log message is required")
            String logMessage
    ) {
    }

    public record RootCauseResponse(
            String rootCause
    ) {
    }
}