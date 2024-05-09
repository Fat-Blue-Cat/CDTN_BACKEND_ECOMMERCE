package org.example.ecommerceweb.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.Product;
import org.example.ecommerceweb.domains.ReviewsRatings;
import org.example.ecommerceweb.domains.User;
import org.example.ecommerceweb.dto.req.ReviewsRatingsReq;
import org.example.ecommerceweb.repository.ProductRepository;
import org.example.ecommerceweb.repository.ReviewsRatingsRepository;
import org.example.ecommerceweb.service.ReviewsRatingsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewsRatingsServiceImpl implements ReviewsRatingsService {
    private final ProductRepository productRepository;
    private final ReviewsRatingsRepository reviewsRatingsRepository;

    @Override
    public ReviewsRatings save(ReviewsRatingsReq req, User user) {
        Product product = productRepository.findById(req.getProductId()).get();
        ReviewsRatings reviewsRatings = ReviewsRatings.builder()
                .product(product)
                .review(req.getReview())
                .rating(req.getRating())
                .user(user)
                .createdAt(new Date())
                .build();
        return reviewsRatingsRepository.save(reviewsRatings);
    }

    @Override
    public ReviewsRatings update(ReviewsRatingsReq req, User user, Long id) {
        ReviewsRatings reviewsRatings = reviewsRatingsRepository.findById(id).get();
        reviewsRatings.setReview(req.getReview());
        reviewsRatings.setRating(req.getRating());

        return reviewsRatingsRepository.save(reviewsRatings);
    }

    @Override
    public void delete(Long id) {
        reviewsRatingsRepository.deleteById(id);
    }

    @Override
    public ReviewsRatings findById(Long id) {
        return reviewsRatingsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    @Override
    public List<ReviewsRatings> findAllByProductId(Long productId) {
        return reviewsRatingsRepository.findAllByProductId(productId);
    }
}
