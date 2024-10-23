package api.kgu.nufarm.application.notification.service;

import api.kgu.nufarm.application.notification.dao.NotificationRepository;
import api.kgu.nufarm.application.notification.dto.NotificationResponseDto;
import api.kgu.nufarm.application.notification.entity.Notification;
import api.kgu.nufarm.application.notification.entity.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public Long saveNotification() {
        LocalDateTime now = LocalDateTime.now();
        Notification notification = Notification.of(NotificationType.ANOMALY, "상추", now);
        Notification result = notificationRepository.save(notification);
        return result.getId();
    }

    public List<NotificationResponseDto> getNotification() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream()
                .sorted(Comparator.comparing(Notification::getTime).reversed())
                .map(notification -> NotificationResponseDto.toDto(notification, notification.getUserItem() + "에 이상이 감지되었습니다."))
                .toList();
    }
}
