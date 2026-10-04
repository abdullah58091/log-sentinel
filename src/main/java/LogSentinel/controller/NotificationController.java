package LogSentinel.controller;

import LogSentinel.dto.NotificationResponse;
import LogSentinel.entity.User;
import LogSentinel.service.NotificationService;
import LogSentinel.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    public NotificationController(
            NotificationService notificationService,
            UserService userService) {

        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getNotifications(
            Authentication authentication) {

        User user =
                userService.getUserByUsername(
                        authentication.getName()
                );

        List<NotificationResponse> notifications =
                notificationService.getUserNotifications(
                        user.getId()
                );

        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount(
            Authentication authentication) {

        User user =
                userService.getUserByUsername(
                        authentication.getName()
                );

        long unreadCount =
                notificationService.getUnreadCount(
                        user.getId()
                );

        return ResponseEntity.ok(unreadCount);
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable Long notificationId,
            Authentication authentication) {

        User user =
                userService.getUserByUsername(
                        authentication.getName()
                );

        notificationService.markAsRead(
                notificationId,
                user.getId()
        );

        return ResponseEntity.noContent().build();
    }
}