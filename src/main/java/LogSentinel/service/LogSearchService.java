package LogSentinel.service;

import LogSentinel.document.LogDocument;
import LogSentinel.repository.LogSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class LogSearchService {

    private final LogSearchRepository logSearchRepository;

    public LogSearchService(LogSearchRepository logSearchRepository) {
        this.logSearchRepository = logSearchRepository;
    }

    public LogDocument save(LogDocument logDocument) {
        return logSearchRepository.save(logDocument);
    }
}