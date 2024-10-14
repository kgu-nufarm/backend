package api.kgu.nufarm.application.cart.api;

import api.kgu.nufarm.application.cart.dto.AddCartRequestDto;
import api.kgu.nufarm.application.cart.dto.CartResponseDto;
import api.kgu.nufarm.application.cart.dto.RemoveCartRequestDto;
import api.kgu.nufarm.application.cart.entity.Cart;
import api.kgu.nufarm.application.cart.service.CartService;
import api.kgu.nufarm.common.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
@Tag(name = "Cart", description = "장바구니")
public class CartController {

    private final CartService cartService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "장바구니 추가")
    @PostMapping("/add")
    public ApiResponse<Long> addToCart(
            @RequestBody AddCartRequestDto dto
    ) {
        Cart cart = cartService.addToCart(dto.getItemId(), dto.getQuantity());
        return ApiResponse.success(cart.getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "장바구니 제거")
    @DeleteMapping("/remove")
    public ApiResponse<Long> removeFromCart(
            @RequestBody RemoveCartRequestDto dto
    ) {
        Cart cart = cartService.removeFromCart(dto.getItemId());
        return ApiResponse.success(cart.getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "장바구니 조회")
    @GetMapping("/get")
    public ApiResponse<List<CartResponseDto>> getCart() {
        List<CartResponseDto> cartItems = cartService.getMyCartItems();
        return ApiResponse.success(cartItems);
    }
}
