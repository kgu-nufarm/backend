package api.kgu.nufarm.application.cart.dto;

import api.kgu.nufarm.application.cart.entity.CartItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartSimpleResponseDto {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Integer quantity;
    private Integer price;

    public static CartSimpleResponseDto toDto(CartItem cartItem) {
        return CartSimpleResponseDto.builder()
                .id(cartItem.getItem().getId())
                .name(cartItem.getItem().getName())
                .description(cartItem.getItem().getDescription())
                .imageUrl(cartItem.getItem().getImageUrl())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getItem().getPrice() * cartItem.getQuantity())
                .build();
    }
}
