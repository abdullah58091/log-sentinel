package LogSentinel.service;

import LogSentinel.document.LogDocument;
import LogSentinel.repository.LogSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogSearchService {

    private final LogSearchRepository logSearchRepository;

    public LogSearchService(LogSearchRepository logSearchRepository) {
        this.logSearchRepository = logSearchRepository;
    }

    public LogDocument save(LogDocument logDocument) {
        return logSearchRepository.save(logDocument);
    }

    public List<LogDocument> searchByMessage(String message) {
        return logSearchRepository.findByMessageContaining(message);
    }

    public List<LogDocument> searchByLevel(String level) {
        return logSearchRepository.findByLevel(level);
    }

    public List<LogDocument> searchBySeverity(String severity) {
        return logSearchRepository.findBySeverity(severity);
    }

    public List<LogDocument> searchBySource(String source) {
        return logSearchRepository.findBySource(source);
    }

    public List<LogDocument> searchByTimestamp(
            LocalDateTime from,
            LocalDateTime to) {

        return logSearchRepository.findByTimestampBetween(
                from,
                to
        );
    }

    public Page<LogDocument> searchByMessage(
            String message,
            Pageable pageable) {

        return logSearchRepository.findByMessageContaining(
                message,
                pageable
        );
    }

    public Page<LogDocument> searchByLevel(
            String level,
            Pageable pageable) {

        return logSearchRepository.findByLevel(
                level,
                pageable
        );
    }

    public Page<LogDocument> searchBySeverity(
            String severity,
            Pageable pageable) {

        return logSearchRepository.findBySeverity(
                severity,
                pageable
        );
    }

    public Page<LogDocument> searchBySource(
            String source,
            Pageable pageable) {

        return logSearchRepository.findBySource(
                source,
                pageable
        );
    }
}