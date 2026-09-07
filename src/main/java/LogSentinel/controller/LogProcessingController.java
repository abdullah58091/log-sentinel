package LogSentinel.controller;

import LogSentinel.dto.ProcessedLogResponse;
import LogSentinel.dto.RawLogRequest;
import LogSentinel.service.LogProcessingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/log-processing")
public class LogProcessingController {

    private final LogProcessingService logProcessingService;

    public LogProcessingController(
            LogProcessingService logProcessingService
    ) {
        this.logProcessingService = logProcessingService;
    }

    @PostMapping
    public ResponseEntity<ProcessedLogResponse> processLog(
            @Valid @RequestBody RawLogRequest request
    ) {

        ProcessedLogResponse response =
                logProcessingService.process(
                        request.getRawLog()
                );

        return ResponseEntity.ok(response);
    }
}
