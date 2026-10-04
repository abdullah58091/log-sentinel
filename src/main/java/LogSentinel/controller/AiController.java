package LogSentinel.controller;

import LogSentinel.dto.AiLogRequest;
import LogSentinel.dto.AiLogResponse;
import LogSentinel.service.AiService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<AiLogResponse> analyzeLog(
            @Valid @RequestBody AiLogRequest request) {

        String analysis = aiService.analyzeLog(request.getLogMessage());

        return ResponseEntity.ok(new AiLogResponse(analysis));
    }
}