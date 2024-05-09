package org.example.ecommerceweb.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewsRatingsReq {
    private Long productId;
    private String review;
    private Double rating;

}
