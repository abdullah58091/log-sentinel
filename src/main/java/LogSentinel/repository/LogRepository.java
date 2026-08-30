package LogSentinel.repository;

import LogSentinel.entity.Log;
import LogSentinel.entity.LogLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {

    List<Log> findByLevel(LogLevel level);

    List<Log> findBySource(String source);
}