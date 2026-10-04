package LogSentinel.repository;

import LogSentinel.entity.Log;
import LogSentinel.entity.LogLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {

    List<Log> findByLevel(LogLevel level);

    Page<Log> findByLevel(
            LogLevel level,
            Pageable pageable
    );

    List<Log> findBySource(String source);

    Page<Log> findBySource(
            String source,
            Pageable pageable
    );

    Page<Log> findByMessageContainingIgnoreCaseOrSourceContainingIgnoreCase(
            String message,
            String source,
            Pageable pageable
    );

    Page<Log> findByTimestampBetween(
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable
    );

    @Query("""
            SELECT l
            FROM Log l
            WHERE
                (:search IS NULL OR
                 LOWER(l.message) LIKE LOWER(CONCAT('%', :search, '%')) OR
                 LOWER(l.source) LIKE LOWER(CONCAT('%', :search, '%')))
            AND
                (:level IS NULL OR l.level = :level)
            AND
                (:source IS NULL OR LOWER(l.source) = LOWER(:source))
            AND
                (:from IS NULL OR l.timestamp >= :from)
            AND
                (:to IS NULL OR l.timestamp <= :to)
            """)
    Page<Log> searchLogsWithFilters(
            @Param("search") String search,
            @Param("level") LogLevel level,
            @Param("source") String source,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            Pageable pageable
    );

    boolean existsByLevelAndMessageAndSource(
            LogLevel level,
            String message,
            String source
    );

    long countByLevel(LogLevel level);
}