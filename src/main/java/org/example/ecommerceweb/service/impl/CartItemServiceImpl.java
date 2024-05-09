package org.example.ecommerceweb.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.*;
import org.example.ecommerceweb.repository.CartItemRepository;
import org.example.ecommerceweb.repository.ProductRepository;
import org.example.ecommerceweb.service.CartItemService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        CartItem createCartItem = cartItemRepository.save(cartItem);
        return createCartItem;
    }

    @Override
    public CartItem updateCartItem( Long cartItemId, Integer quantity, Double price) throws Exception {

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new Exception("CartItem not found with id: "+cartItemId));
        Product product = cartItem.getProductSkus().getProduct();
        cartItem.setQuantity(quantity);
        cartItem.setPrice(price * quantity);
        cartItem.setDiscountedPrice(price * (1 - Double.valueOf( product.getDiscountPercent())/100) * quantity);


        return cartItemRepository.save(cartItem);
    }



    @Override
    public CartItem isCartItemExist(Cart cart, ProductSkus productSkus, Long userId) {
        CartItem cartItem = cartItemRepository.isCartItemExist(cart,productSkus);

        return cartItem;
    }

    @Override
    public void removeCartItem(Long cartItemId) throws Exception {
        CartItem item = cartItemRepository.findById(cartItemId).orElseThrow(() -> new Exception("CartItem not found with id: "+cartItemId));
        cartItemRepository.deleteById(cartItemId);

    }

//    @Override
//    public CartItem findCartItemById(Long cartItemId) throws Exception {
//        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
//
//        if (opt.isPresent()){
//            return opt.get();
//        }
//        throw new Exception("CartItem not found with id: "+cartItemId);
//    }
}
