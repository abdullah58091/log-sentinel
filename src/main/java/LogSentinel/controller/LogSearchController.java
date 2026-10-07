package LogSentinel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import LogSentinel.document.LogDocument;
import LogSentinel.service.LogSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

        LogDocument savedLog =
                logSearchService.save(logDocument);

        return ResponseEntity.ok(savedLog);
    }

    @GetMapping("/message")
    public ResponseEntity<List<LogDocument>> searchByMessage(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                logSearchService.searchByMessage(keyword)
        );
    }

    @GetMapping("/level")
    public ResponseEntity<List<LogDocument>> searchByLevel(
            @RequestParam String level) {

        return ResponseEntity.ok(
                logSearchService.searchByLevel(level)
        );
    }

    @GetMapping("/severity")
    public ResponseEntity<List<LogDocument>> searchBySeverity(
            @RequestParam String severity) {

        return ResponseEntity.ok(
                logSearchService.searchBySeverity(severity)
        );
    }

    @GetMapping("/source")
    public ResponseEntity<List<LogDocument>> searchBySource(
            @RequestParam String source) {

        return ResponseEntity.ok(
                logSearchService.searchBySource(source)
        );
    }

    @GetMapping("/timestamp")
    public ResponseEntity<List<LogDocument>> searchByTimestamp(
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to) {

        return ResponseEntity.ok(
                logSearchService.searchByTimestamp(from, to)
        );
    }
    @GetMapping("/message/page")
    public ResponseEntity<Page<LogDocument>> searchByMessagePaged(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                logSearchService.searchByMessage(
                        keyword,
                        PageRequest.of(page, size)
                )
        );
    }

    @GetMapping("/level/page")
    public ResponseEntity<Page<LogDocument>> searchByLevelPaged(
            @RequestParam String level,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                logSearchService.searchByLevel(
                        level,
                        PageRequest.of(page, size)
                )
        );
    }

    @GetMapping("/severity/page")
    public ResponseEntity<Page<LogDocument>> searchBySeverityPaged(
            @RequestParam String severity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                logSearchService.searchBySeverity(
                        severity,
                        PageRequest.of(page, size)
                )
        );
    }

    @GetMapping("/source/page")
    public ResponseEntity<Page<LogDocument>> searchBySourcePaged(
            @RequestParam String source,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                logSearchService.searchBySource(
                        source,
                        PageRequest.of(page, size)
                )
        );
    }
}