package api.kgu.nufarm.application.useritem.dto;

import api.kgu.nufarm.application.useritem.entity.UserItem;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserItemResponseDto {

    private Long id;
    private String itemName;
    private String photoUrl;
    private boolean isStared;
    private LocalDateTime addedDate;

    public static UserItemResponseDto toDto(UserItem userItem) {
        return UserItemResponseDto.builder()
                .id(userItem.getId())
                .itemName(userItem.getItem().getName())
                .photoUrl(userItem.getPhotoUrl())
                .isStared(userItem.isStared())
                .addedDate(userItem.getCreatedAt())
                .build();
    }
}
