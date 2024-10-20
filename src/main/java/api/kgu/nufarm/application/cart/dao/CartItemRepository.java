package api.kgu.nufarm.application.cart.dao;

import api.kgu.nufarm.application.Item.entity.Item;
import api.kgu.nufarm.application.cart.entity.Cart;
import api.kgu.nufarm.application.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndItem(Cart cart, Item item);
}
