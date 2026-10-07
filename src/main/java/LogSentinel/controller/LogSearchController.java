package LogSentinel.controller;

import LogSentinel.document.LogDocument;
import LogSentinel.service.LogSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search/logs")
public class LogSearchController {

    private final LogSearchService logSearchService;

    public LogSearchController(LogSearchService logSearchService) {
        this.logSearchService = logSearchService;
    }

    @PostMapping
    public ResponseEntity<LogDocument> saveLog(
            @RequestBody LogDocument logDocument) {

        LogDocument savedLog = logSearchService.save(logDocument);

        return ResponseEntity.ok(savedLog);
    }
}