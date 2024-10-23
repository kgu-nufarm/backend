package api.kgu.nufarm.application.notification.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {

    ANOMALY("이상");

    private final String description;
}
