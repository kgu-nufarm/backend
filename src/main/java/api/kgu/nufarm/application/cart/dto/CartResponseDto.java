package api.kgu.nufarm.application.cart.dto;

import api.kgu.nufarm.application.cart.entity.CartItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartResponseDto {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Integer price;
    private boolean isLike;

    public static CartResponseDto toDto(CartItem cartItem) {
        return CartResponseDto.builder()
                .id(cartItem.getItem().getId())
                .name(cartItem.getItem().getName())
                .description(cartItem.getItem().getDescription())
                .imageUrl(cartItem.getItem().getImageUrl())
                .price(cartItem.getItem().getPrice())
                .isLike(cartItem.getItem().getIsLike())
                .build();
    }
}
