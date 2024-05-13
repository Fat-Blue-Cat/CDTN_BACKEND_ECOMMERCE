package org.example.ecommerceweb.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.Product;
import org.example.ecommerceweb.domains.User;
import org.example.ecommerceweb.repository.ProductRepository;
import org.example.ecommerceweb.repository.UserRepository;
import org.example.ecommerceweb.service.FavoriteService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public void addFavorite(Long userId, Long productId) {
        if(isFavorite(userId, productId)){
            throw new RuntimeException("Product already in favorite list");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        productRepository.findById(productId).ifPresent(product -> {
            user.getLikedProducts().add(product);
            userRepository.save(user);
        });

    }

    @Override
    public void removeFavorite(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        productRepository.findById(productId).ifPresent(product -> {
            user.getLikedProducts().remove(product);
            userRepository.save(user);
        });
    }

    @Override
    public void removeAllFavorites(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.getLikedProducts().clear();
        userRepository.save(user);
    }

    @Override
    public boolean isFavorite(Long userId, Long productId) {

        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")).getLikedProducts().stream().anyMatch(product -> product.getId()==productId);
    }

    @Override
    public Set<Product> getAllFavorite(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")).getLikedProducts();
    }
}
