package LogSentinel.service;

import LogSentinel.dto.DashboardSummaryResponse;
import LogSentinel.entity.LogLevel;
import LogSentinel.enums.IncidentStatus;
import LogSentinel.enums.Severity;
import LogSentinel.repository.IncidentRepository;
import LogSentinel.repository.LogRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final LogRepository logRepository;
    private final IncidentRepository incidentRepository;

    public DashboardService(
            LogRepository logRepository,
            IncidentRepository incidentRepository) {

        this.logRepository = logRepository;
        this.incidentRepository = incidentRepository;
    }

    public DashboardSummaryResponse getSummary() {

        long totalLogs = logRepository.count();

        long errors =
                logRepository.countByLevel(LogLevel.ERROR);

        long critical =
                incidentRepository.countBySeverity(
                        Severity.CRITICAL
                );

        long openIncidents =
                incidentRepository.countByStatus(
                        IncidentStatus.OPEN
                );

        long resolvedIncidents =
                incidentRepository.countByStatus(
                        IncidentStatus.RESOLVED
                );

        return new DashboardSummaryResponse(
                totalLogs,
                errors,
                critical,
                openIncidents,
                resolvedIncidents
        );
    }
}
