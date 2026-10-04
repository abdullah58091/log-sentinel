package LogSentinel.service;

import LogSentinel.dto.NotificationResponse;
import LogSentinel.entity.Incident;
import LogSentinel.entity.Notification;
import LogSentinel.entity.User;
import LogSentinel.repository.NotificationRepository;
import LogSentinel.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(
            NotificationRepository notificationRepository,
            UserRepository userRepository) {

        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createIncidentNotification(
            Incident incident) {

        List<User> developers =
                userRepository.findByRole("DEVELOPER");

        String message =
                "New " + incident.getSeverity()
                        + " incident created: Incident #"
                        + incident.getId();

        for (User developer : developers) {

            Notification notification = new Notification();

            notification.setUser(developer);
            notification.setIncident(incident);
            notification.setType("INCIDENT_CREATED");
            notification.setMessage(message);
            notification.setRead(false);
            notification.setCreatedAt(
                    java.time.LocalDateTime.now()
            );

            notificationRepository.save(notification);
        }
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getUserNotifications(
            Long userId) {

        return notificationRepository
                .findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(NotificationResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public long getUnreadCount(Long userId) {

        return notificationRepository
                .countByUserIdAndReadFalse(userId);
    }

    @Transactional
    public void markAsRead(
            Long notificationId,
            Long userId) {

        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification not found"
                                ));

        if (!notification.getUser().getId().equals(userId)) {

            throw new RuntimeException(
                    "You cannot modify this notification"
            );
        }

        notification.setRead(true);

        notificationRepository.save(notification);
    }
}