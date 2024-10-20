package api.kgu.nufarm.application.Item.dto;

import api.kgu.nufarm.application.Item.entity.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemResponseDto {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;
    private boolean isLike;

    public static ItemResponseDto toDto(Item item, boolean isLike) {
        return ItemResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .imageUrl(item.getImageUrl())
                .isLike(isLike)
                .build();
    }
}
