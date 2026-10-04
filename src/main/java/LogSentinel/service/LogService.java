package LogSentinel.service;

import LogSentinel.dto.CreateLogRequest;
import LogSentinel.dto.LogResponse;
import LogSentinel.entity.Log;
import LogSentinel.entity.LogLevel;
import LogSentinel.exception.DuplicateLogException;
import LogSentinel.exception.LogNotFoundException;
import LogSentinel.processor.DuplicateLogDetector;
import LogSentinel.repository.LogRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    // GET ALL LOGS WITH PAGINATION
    public Page<LogResponse> getAllLogs(Pageable pageable) {

        return logRepository.findAll(pageable)
                .map(this::toResponse);
    }

    // SEARCH LOGS
    public Page<LogResponse> searchLogs(
            String search,
            Pageable pageable
    ) {

        return logRepository
                .findByMessageContainingIgnoreCaseOrSourceContainingIgnoreCase(
                        search,
                        search,
                        pageable
                )
                .map(this::toResponse);
    }

    // GET LOGS BY LEVEL WITH PAGINATION
    public Page<LogResponse> getLogsByLevel(
            LogLevel level,
            Pageable pageable
    ) {

        return logRepository.findByLevel(level, pageable)
                .map(this::toResponse);
    }

    // GET LOGS BY SOURCE WITH PAGINATION
    public Page<LogResponse> getLogsBySource(
            String source,
            Pageable pageable
    ) {

        return logRepository.findBySource(source, pageable)
                .map(this::toResponse);
    }

    // GET LOGS BY DATE RANGE WITH PAGINATION
    public Page<LogResponse> getLogsByDateRange(
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable
    ) {

        return logRepository.findByTimestampBetween(
                        from,
                        to,
                        pageable
                )
                .map(this::toResponse);
    }

    // COMBINED SEARCH + FILTERS + PAGINATION
    public Page<LogResponse> searchLogsWithFilters(
            String search,
            LogLevel level,
            String source,
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable
    ) {

        return logRepository.searchLogsWithFilters(
                        search,
                        level,
                        source,
                        from,
                        to,
                        pageable
                )
                .map(this::toResponse);
    }

    // GET LOGS BY LEVEL
    public List<LogResponse> getLogsByLevel(LogLevel level) {

        return logRepository.findByLevel(level)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // GET LOGS BY SOURCE
    public List<LogResponse> getLogsBySource(String source) {

        return logRepository.findBySource(source)
                .stream()
                .map(this::toResponse)
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

        return toResponse(log);
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

    // ENTITY -> RESPONSE
    private LogResponse toResponse(Log log) {

        return new LogResponse(
                log.getId(),
                log.getLevel(),
                log.getMessage(),
                log.getSource(),
                log.getTimestamp()
        );
    }
}