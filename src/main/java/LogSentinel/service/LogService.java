package LogSentinel.service;

import LogSentinel.exception.LogNotFoundException;
import LogSentinel.dto.CreateLogRequest;
import LogSentinel.dto.LogResponse;
import LogSentinel.entity.Log;
import LogSentinel.entity.LogLevel;
import LogSentinel.repository.LogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public LogResponse createLog(CreateLogRequest request) {

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

    public LogResponse getLogById(Long id) {

        Log log = logRepository.findById(id)
                .orElseThrow(() ->
                        new LogNotFoundException("Log not found with id: " + id));

        return new LogResponse(
                log.getId(),
                log.getLevel(),
                log.getMessage(),
                log.getSource(),
                log.getTimestamp()
        );
    }

    public void deleteLog(Long id) {

        if (!logRepository.existsById(id)) {
            throw new LogNotFoundException("Log not found with id: " + id);
        }

        logRepository.deleteById(id);
    }
}