package org.example.ecommerceweb.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.*;
import org.example.ecommerceweb.dto.req.AddItemRequestDto;
import org.example.ecommerceweb.repository.CartItemRepository;
import org.example.ecommerceweb.repository.CartRepository;
import org.example.ecommerceweb.repository.ProductRepository;
import org.example.ecommerceweb.repository.ProductSkusRepository;
import org.example.ecommerceweb.service.CartItemService;
import org.example.ecommerceweb.service.CartService;
//import org.example.ecommerceweb.service.ProductService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
//    private final ProductService productService;
    private final ProductRepository productRepository;
    private final CartItemService cartItemService;
    private final CartItemRepository cartItemRepository;
    private final ProductSkusRepository productSkusRepository;

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        System.out.println(user);
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequestDto req) throws Exception {
        Cart cart = cartRepository.findByUserId(userId);
        ProductSkus productSkus = productSkusRepository.findById(req.getProductSkuId()).get();
        CartItem isPresent = cartItemService.isCartItemExist(cart,productSkus,userId);

        if (isPresent == null) {
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .productSkus(productSkus)
                    .quantity(req.getQuantity())
//                    .price(productSkus.getPrice() * req.getQuantity())
//                    .discount(productSkus.getProduct().getDiscountPercent())
//                    .discountedPrice(req.getPrice()* req.getQuantity()*(1 - Double.valueOf(productSkus.getProduct().getDiscountPercent())/100))
                    .build();
            CartItem createCartItem = cartItemService.createCartItem(cartItem);

        }else{
            isPresent.setQuantity(isPresent.getQuantity() + req.getQuantity());
//            isPresent.setPrice(isPresent.getPrice() + req.getPrice() * req.getQuantity());
//            isPresent.setDiscountedPrice(isPresent.getDiscountedPrice() + (req.getPrice()*req.getQuantity()*(1 - Double.valueOf(productSkus.getProduct().getDiscountPercent())/100)));
            cartItemRepository.save(isPresent);
        }

        return "Item add to cart!";

    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);

        double totalPrice = 0;
        double totalDiscountedPrice = 0;
        int totalItem =0;

        for (CartItem cartItem : cart.getCartItems()){
            totalPrice = totalPrice + cartItem.getProductSkus().getPrice() * cartItem.getQuantity();
            totalDiscountedPrice =totalDiscountedPrice + cartItem.getProductSkus().getPrice() * cartItem.getQuantity()* (1 - Double.valueOf(cartItem.getProductSkus().getProduct().getDiscountPercent())/100);
            totalItem = totalItem +cartItem.getQuantity();
        }

        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);
//        cart.setTotalDiscount(totalPrice-totalDiscountedPrice);
        cartRepository.save(cart);

        return cart;
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            cart.getCartItems().clear();
//            cart.setTotalDiscount(0d);
            cart.setTotalDiscountedPrice(0d);
            cart.setTotalItem(0);
            cart.setTotalPrice(0d);
            cartRepository.save(cart);
        }
    }
}
