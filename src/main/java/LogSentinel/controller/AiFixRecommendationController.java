package LogSentinel.controller;

import LogSentinel.service.AiFixRecommendationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiFixRecommendationController {

    private final AiFixRecommendationService aiFixRecommendationService;

    public AiFixRecommendationController(
            AiFixRecommendationService aiFixRecommendationService) {
        this.aiFixRecommendationService = aiFixRecommendationService;
    }

    @PostMapping("/fix")
    public ResponseEntity<FixResponse> recommendFix(
            @Valid @RequestBody FixRequest request) {

        String recommendation =
                aiFixRecommendationService.recommendFix(request.logMessage());

        return ResponseEntity.ok(new FixResponse(recommendation));
    }

    public record FixRequest(
            @NotBlank(message = "Log message is required")
            String logMessage
    ) {
    }

    public record FixResponse(
            String recommendation
    ) {
    }
}