package LogSentinel.repository;

import LogSentinel.document.LogDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LogSearchRepository
        extends ElasticsearchRepository<LogDocument, Long> {
}