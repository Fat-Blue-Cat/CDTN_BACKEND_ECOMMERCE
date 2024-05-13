package org.example.ecommerceweb.service;

import org.example.ecommerceweb.domains.Product;

import java.util.Set;

public interface FavoriteService {
    void addFavorite(Long userId, Long productId);

    void removeFavorite(Long userId, Long productId);

    void removeAllFavorites(Long userId);

    boolean isFavorite(Long userId, Long productId);

    Set<Product> getAllFavorite(Long userId);
}
