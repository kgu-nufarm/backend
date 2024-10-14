package api.kgu.nufarm.application.cart.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddCartRequestDto {

    private Long itemId;
    private int quantity;
}
