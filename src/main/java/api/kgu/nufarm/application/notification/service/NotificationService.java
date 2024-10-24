package api.kgu.nufarm.application.notification.service;

import api.kgu.nufarm.application.notification.dao.NotificationRepository;
import api.kgu.nufarm.application.notification.dto.NotificationResponseDto;
import api.kgu.nufarm.application.notification.entity.Notification;
import api.kgu.nufarm.application.notification.entity.NotificationType;
import api.kgu.nufarm.application.user.entity.User;
import api.kgu.nufarm.application.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public Long saveNotification(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        User user = userService.findById(userId);
        Notification notification = Notification.of(NotificationType.ANOMALY, "상추", user, now);
        Notification result = notificationRepository.save(notification);
        return result.getId();
    }

    public List<NotificationResponseDto> getNotification() {
        User currentUser = userService.getCurrentUser();
        List<Notification> notifications = notificationRepository.findByUserId(currentUser.getId());
        return notifications.stream()
                .sorted(Comparator.comparing(Notification::getTime).reversed())
                .map(notification -> NotificationResponseDto.toDto(notification, notification.getUserItem() + "에 이상이 감지되었습니다."))
                .toList();
    }
}
