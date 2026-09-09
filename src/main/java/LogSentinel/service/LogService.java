package LogSentinel.service;

import LogSentinel.dto.CreateLogRequest;
import LogSentinel.dto.LogResponse;
import LogSentinel.entity.Log;
import LogSentinel.entity.LogLevel;
import LogSentinel.exception.LogNotFoundException;
import LogSentinel.processor.DuplicateLogDetector;
import LogSentinel.repository.LogRepository;
import org.springframework.stereotype.Service;
import LogSentinel.exception.DuplicateLogException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogService {

    private final LogRepository logRepository;
    private final DuplicateLogDetector duplicateLogDetector;

    public LogService(
            LogRepository logRepository,
            DuplicateLogDetector duplicateLogDetector
    ) {
        this.logRepository = logRepository;
        this.duplicateLogDetector = duplicateLogDetector;
    }

    // CREATE LOG
    public LogResponse createLog(CreateLogRequest request) {

        // Check duplicate before saving
        boolean duplicate = duplicateLogDetector.isDuplicate(
                request.getLevel(),
                request.getMessage(),
                request.getSource()
        );

        if (duplicate) {
            throw new DuplicateLogException("Duplicate log detected");
        }

        Log log = new Log();

        log.setLevel(request.getLevel());
        log.setMessage(request.getMessage());
        log.setSource(request.getSource());
        log.setTimestamp(LocalDateTime.now());

        Log savedLog = logRepository.save(log);

        return new LogResponse(
                savedLog.getId(),
                savedLog.getLevel(),
                savedLog.getMessage(),
                savedLog.getSource(),
                savedLog.getTimestamp()
        );
    }

    // GET ALL LOGS
    public List<LogResponse> getAllLogs() {

        return logRepository.findAll()
                .stream()
                .map(log -> new LogResponse(
                        log.getId(),
                        log.getLevel(),
                        log.getMessage(),
                        log.getSource(),
                        log.getTimestamp()
                ))
                .toList();
    }

    // GET LOGS BY LEVEL
    public List<LogResponse> getLogsByLevel(LogLevel level) {

        return logRepository.findByLevel(level)
                .stream()
                .map(log -> new LogResponse(
                        log.getId(),
                        log.getLevel(),
                        log.getMessage(),
                        log.getSource(),
                        log.getTimestamp()
                ))
                .toList();
    }

    // GET LOGS BY SOURCE
    public List<LogResponse> getLogsBySource(String source) {

        return logRepository.findBySource(source)
                .stream()
                .map(log -> new LogResponse(
                        log.getId(),
                        log.getLevel(),
                        log.getMessage(),
                        log.getSource(),
                        log.getTimestamp()
                ))
                .toList();
    }

    // GET LOG BY ID
    public LogResponse getLogById(Long id) {

        Log log = logRepository.findById(id)
                .orElseThrow(() ->
                        new LogNotFoundException(
                                "Log not found with id: " + id
                        )
                );

        return new LogResponse(
                log.getId(),
                log.getLevel(),
                log.getMessage(),
                log.getSource(),
                log.getTimestamp()
        );
    }

    // DELETE LOG
    public void deleteLog(Long id) {

        if (!logRepository.existsById(id)) {
            throw new LogNotFoundException(
                    "Log not found with id: " + id
            );
        }

        logRepository.deleteById(id);
    }
}