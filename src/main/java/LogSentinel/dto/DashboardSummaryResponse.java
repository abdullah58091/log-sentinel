package LogSentinel.dto;

public class DashboardSummaryResponse {

    private long totalLogs;
    private long errors;
    private long critical;
    private long openIncidents;
    private long resolvedIncidents;

    public DashboardSummaryResponse() {
    }

    public DashboardSummaryResponse(
            long totalLogs,
            long errors,
            long critical,
            long openIncidents,
            long resolvedIncidents) {

        this.totalLogs = totalLogs;
        this.errors = errors;
        this.critical = critical;
        this.openIncidents = openIncidents;
        this.resolvedIncidents = resolvedIncidents;
    }

    public long getTotalLogs() {
        return totalLogs;
    }

    public long getErrors() {
        return errors;
    }

    public long getCritical() {
        return critical;
    }

    public long getOpenIncidents() {
        return openIncidents;
    }

    public long getResolvedIncidents() {
        return resolvedIncidents;
    }
}

