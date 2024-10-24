package api.kgu.nufarm.application.notification.api;

import api.kgu.nufarm.application.notification.dto.NotificationResponseDto;
import api.kgu.nufarm.application.notification.service.NotificationService;
import api.kgu.nufarm.common.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
@Tag(name = "Notification", description = "사용자 알림")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "알림 저장")
    @GetMapping("/save")
    public ApiResponse<Long> save(
            @RequestParam Long userId
    ) {
        Long id = notificationService.saveNotification(userId);
        return ApiResponse.success(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "알림 조회")
    @GetMapping("/get")
    public ApiResponse<List<NotificationResponseDto>> getNotification() {
        List<NotificationResponseDto> notifications = notificationService.getNotification();
        return ApiResponse.success(notifications);
    }
}
