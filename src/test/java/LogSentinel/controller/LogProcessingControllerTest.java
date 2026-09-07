package LogSentinel.controller;

import LogSentinel.dto.ProcessedLogResponse;
import LogSentinel.dto.RawLogRequest;
import LogSentinel.entity.LogLevel;
import LogSentinel.service.LogProcessingService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogProcessingControllerTest {

    private final LogProcessingService logProcessingService =
            mock(LogProcessingService.class);

    private final LogProcessingController controller =
            new LogProcessingController(logProcessingService);

    @Test
    void shouldProcessLogSuccessfully() {

        RawLogRequest request = new RawLogRequest();

        request.setRawLog(
                "2026-09-07T09:30:00 ERROR [database-service] Database connection failed"
        );

        LocalDateTime timestamp =
                LocalDateTime.of(2026, 9, 7, 9, 30, 0);

        ProcessedLogResponse processedResponse =
                new ProcessedLogResponse(
                        LogLevel.ERROR,
                        "Database connection failed",
                        "database-service",
                        timestamp,
                        true,
                        false
                );

        when(logProcessingService.process(request.getRawLog()))
                .thenReturn(processedResponse);

        ResponseEntity<ProcessedLogResponse> response =
                controller.processLog(request);

        assertEquals(200, response.getStatusCode().value());

        assertNotNull(response.getBody());

        assertEquals(
                LogLevel.ERROR,
                response.getBody().getLevel()
        );

        assertEquals(
                "Database connection failed",
                response.getBody().getMessage()
        );

        assertEquals(
                "database-service",
                response.getBody().getSource()
        );

        assertTrue(
                response.getBody().isAbnormal()
        );

        assertFalse(
                response.getBody().isDuplicate()
        );

        verify(logProcessingService)
                .process(request.getRawLog());
    }
}
