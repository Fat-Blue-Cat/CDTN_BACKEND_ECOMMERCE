package org.example.ecommerceweb.service;

import org.example.ecommerceweb.domains.ReviewsRatings;
import org.example.ecommerceweb.domains.User;
import org.example.ecommerceweb.dto.req.ReviewsRatingsReq;
import org.example.ecommerceweb.dto.response.reviewsAndRatings.ReviewsAndRatingsResponseDto;

import java.util.List;

public interface ReviewsRatingsService {
    ReviewsRatings save(ReviewsRatingsReq req, User user);
    ReviewsRatings update(ReviewsRatingsReq req, User user, Long id);
    void delete(Long id);
    ReviewsRatings findById(Long id);
    ReviewsAndRatingsResponseDto findAllByProductId(Long productId);

}
