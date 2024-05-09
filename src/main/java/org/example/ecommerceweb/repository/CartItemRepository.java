package org.example.ecommerceweb.repository;

import org.example.ecommerceweb.domains.Cart;
import org.example.ecommerceweb.domains.CartItem;
import org.example.ecommerceweb.domains.Product;
import org.example.ecommerceweb.domains.ProductSkus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart = :cart AND ci.productSkus = :productSkus ")
    public CartItem isCartItemExist(@Param("cart") Cart cart, @Param("productSkus") ProductSkus productSkus);
//    @Modifying
//    @Query("DELETE FROM CartItem ci WHERE ci.userId = :userId")
//    void deleteCartItemsByCartId(@Param("userId") Long userId);

}
