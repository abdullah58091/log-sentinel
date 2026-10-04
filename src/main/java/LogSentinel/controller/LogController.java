package LogSentinel.controller;

import LogSentinel.dto.CreateLogRequest;
import LogSentinel.dto.LogResponse;
import LogSentinel.entity.LogLevel;
import LogSentinel.service.LogService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    // CREATE LOG
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LogResponse createLog(
            @Valid @RequestBody CreateLogRequest request
    ) {
        return logService.createLog(request);
    }

    // GET ALL LOGS + COMBINED FILTERS + PAGINATION + SORTING
    @GetMapping
    public Page<LogResponse> getAllLogs(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) LogLevel level,
            @RequestParam(required = false) String source,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            Pageable pageable
    ) {

        return logService.searchLogsWithFilters(
                search,
                level,
                source,
                from,
                to,
                pageable
        );
    }

    // GET LOGS BY LEVEL
    @GetMapping("/filter/level/{level}")
    public List<LogResponse> getLogsByLevel(
            @PathVariable LogLevel level
    ) {
        return logService.getLogsByLevel(level);
    }

    // GET LOGS BY SOURCE
    @GetMapping("/filter/source/{source}")
    public List<LogResponse> getLogsBySource(
            @PathVariable String source
    ) {
        return logService.getLogsBySource(source);
    }

    // GET LOG BY ID
    @GetMapping("/{id}")
    public LogResponse getLogById(
            @PathVariable Long id
    ) {
        return logService.getLogById(id);
    }

    // DELETE LOG
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLog(
            @PathVariable Long id
    ) {
        logService.deleteLog(id);
    }
}