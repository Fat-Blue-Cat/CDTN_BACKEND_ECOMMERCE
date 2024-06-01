package org.example.ecommerceweb.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.Product;
import org.example.ecommerceweb.domains.ReviewsRatings;
import org.example.ecommerceweb.domains.User;
import org.example.ecommerceweb.dto.req.ReviewsRatingsReq;
import org.example.ecommerceweb.dto.response.reviewsAndRatings.ReviewsAndRatingsResponseDto;
import org.example.ecommerceweb.mapper.Mapstruct;
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
    private final Mapstruct mapstruct;

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
    public ReviewsAndRatingsResponseDto findAllByProductId(Long productId) {
        List<ReviewsRatings> reviewsRatingsList = reviewsRatingsRepository.findAllByProductId(productId);
        return ReviewsAndRatingsResponseDto.builder()
                .reviewsRatingsList(mapstruct.mapToReviewsRatingResponseDtoList(reviewsRatingsList))
                .averageRating(Math.round(reviewsRatingsList.stream().mapToDouble(ReviewsRatings::getRating).average().orElse(0) * 100.0) / 100.0)
                .totalReviews(reviewsRatingsList.size())
                .build();
    }
}
