package LogSentinel.repository;

import LogSentinel.document.LogDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LogSearchRepository
        extends ElasticsearchRepository<LogDocument, String> {

    List<LogDocument> findByMessageContaining(String message);

    List<LogDocument> findByLevel(String level);

    List<LogDocument> findBySeverity(String severity);

    List<LogDocument> findBySource(String source);

    List<LogDocument> findByTimestampBetween(
            LocalDateTime from,
            LocalDateTime to
    );

    Page<LogDocument> findByMessageContaining(
            String message,
            Pageable pageable
    );

    Page<LogDocument> findByLevel(
            String level,
            Pageable pageable
    );

    Page<LogDocument> findBySeverity(
            String severity,
            Pageable pageable
    );

    Page<LogDocument> findBySource(
            String source,
            Pageable pageable
    );
}