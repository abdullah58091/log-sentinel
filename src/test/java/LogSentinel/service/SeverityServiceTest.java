package LogSentinel.service;

import LogSentinel.entity.LogLevel;
import LogSentinel.enums.Severity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeverityServiceTest {

    private final SeverityService severityService =
            new SeverityService();

    @Test
    void shouldReturnLowSeverityForTrace() {

        assertEquals(
                Severity.LOW,
                severityService.determineSeverity(LogLevel.TRACE)
        );
    }

    @Test
    void shouldReturnLowSeverityForDebug() {

        assertEquals(
                Severity.LOW,
                severityService.determineSeverity(LogLevel.DEBUG)
        );
    }

    @Test
    void shouldReturnLowSeverityForInfo() {

        assertEquals(
                Severity.LOW,
                severityService.determineSeverity(LogLevel.INFO)
        );
    }

    @Test
    void shouldReturnMediumSeverityForWarn() {

        assertEquals(
                Severity.MEDIUM,
                severityService.determineSeverity(LogLevel.WARN)
        );
    }

    @Test
    void shouldReturnHighSeverityForError() {

        assertEquals(
                Severity.HIGH,
                severityService.determineSeverity(LogLevel.ERROR)
        );
    }

    @Test
    void shouldReturnCriticalSeverityForFatal() {

        assertEquals(
                Severity.CRITICAL,
                severityService.determineSeverity(LogLevel.FATAL)
        );
    }

    @Test
    void shouldRejectNullLogLevel() {

        assertThrows(
                IllegalArgumentException.class,
                () -> severityService.determineSeverity(null)
        );
    }

    // Context-based severity tests

    @Test
    void shouldReturnCriticalForCompletelyUnavailableDatabase() {

        assertEquals(
                Severity.CRITICAL,
                severityService.determineSeverity(
                        LogLevel.ERROR,
                        "Database completely unavailable"
                )
        );
    }

    @Test
    void shouldReturnCriticalForProductionDatabaseIssue() {

        assertEquals(
                Severity.CRITICAL,
                severityService.determineSeverity(
                        LogLevel.ERROR,
                        "Production database connection failed"
                )
        );
    }

    @Test
    void shouldReturnCriticalForCompletelyDownSystem() {

        assertEquals(
                Severity.CRITICAL,
                severityService.determineSeverity(
                        LogLevel.ERROR,
                        "System completely down"
                )
        );
    }

    @Test
    void shouldReturnHighForNormalErrorMessage() {

        assertEquals(
                Severity.HIGH,
                severityService.determineSeverity(
                        LogLevel.ERROR,
                        "Database connection failed"
                )
        );
    }

    @Test
    void shouldReturnMediumForNormalWarningMessage() {

        assertEquals(
                Severity.MEDIUM,
                severityService.determineSeverity(
                        LogLevel.WARN,
                        "Payment response is slow"
                )
        );
    }

    @Test
    void shouldFallbackToLevelWhenMessageIsEmpty() {

        assertEquals(
                Severity.HIGH,
                severityService.determineSeverity(
                        LogLevel.ERROR,
                        ""
                )
        );
    }

    @Test
    void shouldFallbackToLevelWhenMessageIsNull() {

        assertEquals(
                Severity.HIGH,
                severityService.determineSeverity(
                        LogLevel.ERROR,
                        null
                )
        );
    }
}