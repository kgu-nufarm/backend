package api.kgu.nufarm.application.cart.dto;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddCartRequestDto {

    private Long itemId;

    @Min(1)
    private int quantity;
}
