package api.kgu.nufarm.application.cart.dto;

import api.kgu.nufarm.application.cart.entity.CartItem;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CartResponseDto {

    List<CartSimpleResponseDto> items;
    private Integer totalPrice;

    public static CartResponseDto of(List<CartSimpleResponseDto> items, Integer totalPrice) {
        return CartResponseDto.builder()
                .items(items)
                .totalPrice(totalPrice)
                .build();
    }
}
