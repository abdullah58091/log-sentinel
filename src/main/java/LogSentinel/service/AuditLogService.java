package LogSentinel.service;

import LogSentinel.entity.AuditLog;
import LogSentinel.entity.User;
import LogSentinel.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void recordAction(
            User user,
            String action,
            String details
    ) {

        AuditLog auditLog = new AuditLog(
                user,
                action,
                LocalDateTime.now(),
                details
        );

        auditLogRepository.save(auditLog);
    }
}