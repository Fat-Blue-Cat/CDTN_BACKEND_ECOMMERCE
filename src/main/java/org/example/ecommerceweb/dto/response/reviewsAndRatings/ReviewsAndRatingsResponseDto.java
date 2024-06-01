package org.example.ecommerceweb.dto.response.reviewsAndRatings;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.ecommerceweb.domains.ReviewsRatings;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Builder
public class ReviewsAndRatingsResponseDto {
    private List<ReviewsRatingResponseDto> reviewsRatingsList;
    private Double averageRating;
    private Integer totalReviews;

}
