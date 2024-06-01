package org.example.ecommerceweb.dto.response.reviewsAndRatings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.ecommerceweb.domains.Product;
import org.example.ecommerceweb.domains.User;
import org.example.ecommerceweb.dto.response.UserResponseDto;

import java.util.Date;

@Getter
@Setter
public class ReviewsRatingResponseDto {
    private Long id;
    private Long productId;
    private UserResponseDto user;
    private Double rating;
    private String review;
    private Date createdAt;
}
