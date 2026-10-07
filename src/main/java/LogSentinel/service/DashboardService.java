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

    private static final String DASHBOARD_CACHE_KEY =
            "dashboard:summary";

    private static final long CACHE_TTL_SECONDS = 60;

    private final LogRepository logRepository;
    private final IncidentRepository incidentRepository;
    private final RedisService redisService;

    public DashboardService(
            LogRepository logRepository,
            IncidentRepository incidentRepository,
            RedisService redisService) {

        this.logRepository = logRepository;
        this.incidentRepository = incidentRepository;
        this.redisService = redisService;
    }

    public DashboardSummaryResponse getSummary() {

        Object cachedData =
                redisService.get(DASHBOARD_CACHE_KEY);

        if (cachedData instanceof DashboardSummaryResponse cachedSummary) {
            return cachedSummary;
        }

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

        DashboardSummaryResponse summary =
                new DashboardSummaryResponse(
                        totalLogs,
                        errors,
                        critical,
                        openIncidents,
                        resolvedIncidents
                );

        redisService.set(
                DASHBOARD_CACHE_KEY,
                summary,
                CACHE_TTL_SECONDS
        );

        return summary;
    }
}