package org.example.ecommerceweb.service;

import org.example.ecommerceweb.domains.*;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);
////
    public CartItem updateCartItem(Long cartItemId,Integer quantity, Double price) throws Exception;
////
    public CartItem isCartItemExist(Cart cart, ProductSkus productSkus,Long userId);
////
    public void removeCartItem(Long cartItemId) throws Exception;
////
//    public CartItem findCartItemById(Long cartItemId) throws Exception;
}
