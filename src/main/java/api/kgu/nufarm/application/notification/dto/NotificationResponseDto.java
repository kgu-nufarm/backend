package api.kgu.nufarm.application.notification.dto;

import api.kgu.nufarm.application.notification.entity.Notification;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationResponseDto {

    private Long id;
    private String message;
    private LocalDateTime time;

    public static NotificationResponseDto toDto(Notification notification, String message) {
        return NotificationResponseDto.builder()
                .id(notification.getId())
                .message(message)
                .time(notification.getTime())
                .build();
    }
}
