package LogSentinel.controller;

import LogSentinel.dto.CreateLogRequest;
import LogSentinel.dto.LogResponse;
import LogSentinel.service.LogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LogResponse createLog(@Valid @RequestBody CreateLogRequest request) {
        return logService.createLog(request);
    }

    @GetMapping
    public List<LogResponse> getAllLogs() {
        return logService.getAllLogs();
    }
    @GetMapping("/{id}")
    public LogResponse getLogById(@PathVariable Long id) {
        return logService.getLogById(id);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLog(@PathVariable Long id) {
        logService.deleteLog(id);
    }
}
