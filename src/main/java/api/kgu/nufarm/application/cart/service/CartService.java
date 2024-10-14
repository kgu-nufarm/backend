package api.kgu.nufarm.application.cart.service;

import api.kgu.nufarm.application.Item.entity.Item;
import api.kgu.nufarm.application.Item.service.ItemService;
import api.kgu.nufarm.application.cart.dao.CartItemRepository;
import api.kgu.nufarm.application.cart.dao.CartRepository;
import api.kgu.nufarm.application.cart.dto.CartResponseDto;
import api.kgu.nufarm.application.cart.entity.Cart;
import api.kgu.nufarm.application.cart.entity.CartItem;
import api.kgu.nufarm.application.user.entity.User;
import api.kgu.nufarm.application.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemService itemService;
    private final UserService userService;

    @Transactional
    public Cart addToCart(Long itemId, int quantity) {
        User user = userService.getCurrentUser();
        Item item = itemService.getItem(itemId);
        Cart cart = findCartByUser(user);
        CartItem cartItem = findCartItemByCartAndItem(cart, item);

        cartItem.addQuantity(quantity);
        cartItemRepository.save(cartItem);

        int itemTotalPrice = item.getPrice() * quantity;
        cart.addTotalPrice(itemTotalPrice);

        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeFromCart(Long itemId) {
        User user = userService.getCurrentUser();
        Cart cart = getCart(user.getId());
        CartItem cartItem = getCartItemByCartAndItem(cart, itemService.getItem(itemId));

        cart.minusTotalPrice(cartItem.getItem().getPrice() * cartItem.getQuantity());
        cartItemRepository.delete(cartItem);
        return cartRepository.save(cart);
    }

    public List<CartResponseDto> getMyCartItems() {
        User user = userService.getCurrentUser();
        Cart cart = findCartByUser(user);
        if(cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            return null;
        }
        return cart.getCartItems().stream()
                .map(CartResponseDto::toDto)
                .toList();
    }

    private CartItem findCartItemByCartAndItem(Cart cart, Item item) {
        return cartItemRepository.findByCartAndItem(cart, item)
                .orElse(CartItem.builder()
                        .cart(cart)
                        .item(item)
                        .quantity(0)
                        .build());
    }

    private CartItem getCartItemByCartAndItem(Cart cart, Item item) {
        return cartItemRepository.findByCartAndItem(cart, item)
                .orElseThrow(() -> new IllegalArgumentException("장바구니에 해당 아이템이 없습니다."));
    }

    private Cart findCartByUser(User user) {
        return cartRepository.findByUserId(user.getId())
                .orElse(Cart.builder()
                        .user(user)
                        .totalPrice(0)
                        .build());
    }

    public Cart getCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 장바구니를 찾을 수 없습니다."));
    }
}
