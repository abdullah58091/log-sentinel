package LogSentinel.repository;

import LogSentinel.entity.Incident;
import LogSentinel.enums.IncidentStatus;
import LogSentinel.enums.Severity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

    long countByStatus(IncidentStatus status);

    long countBySeverity(Severity severity);
}