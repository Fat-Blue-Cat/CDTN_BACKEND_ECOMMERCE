package org.example.ecommerceweb.service;

import org.example.ecommerceweb.domains.Cart;
import org.example.ecommerceweb.domains.User;
import org.example.ecommerceweb.dto.req.AddItemRequestDto;

public interface CartService {
    public Cart createCart(User user);
//
    public String addCartItem(Long userId, AddItemRequestDto req) throws Exception;
    Cart findUserCart(Long userId);
    public void clearCart(Long userId);

}
