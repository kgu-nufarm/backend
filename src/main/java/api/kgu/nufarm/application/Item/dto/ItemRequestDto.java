package api.kgu.nufarm.application.Item.dto;

import api.kgu.nufarm.application.Item.entity.Item;
import api.kgu.nufarm.application.Item.entity.ItemCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemRequestDto {

    private String name; // 제품명
    private String description; // 제품 설명
    private Integer price; // 가격
    private ItemCategory category; // 상품 카테고리

    public static Item toEntity(ItemRequestDto itemRequestDto, String imageUrl) {
        return Item.builder()
                .name(itemRequestDto.getName())
                .description(itemRequestDto.getDescription())
                .price(itemRequestDto.getPrice())
                .imageUrl(imageUrl)
                .category(itemRequestDto.getCategory())
                .build();
    }
}
