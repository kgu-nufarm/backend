package api.kgu.nufarm.application.cart.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RemoveCartRequestDto {

    private Long itemId;
}
