package api.kgu.nufarm.application.like.dto;

import api.kgu.nufarm.application.like.entity.LikeItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeResponseDto {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Integer price;
    private boolean isLike;

    public static LikeResponseDto toDto(LikeItem likeItem) {
        return LikeResponseDto.builder()
                .id(likeItem.getItem().getId())
                .name(likeItem.getItem().getName())
                .description(likeItem.getItem().getDescription())
                .imageUrl(likeItem.getItem().getImageUrl())
                .price(likeItem.getItem().getPrice())
                .isLike(likeItem.getItem().getIsLike())
                .build();
    }
}
