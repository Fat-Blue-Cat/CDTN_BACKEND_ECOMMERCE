package org.example.ecommerceweb.repository;

import org.example.ecommerceweb.domains.ReviewsRatings;
import org.example.ecommerceweb.dto.response.projection.IAverageRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRatingsRepository extends JpaRepository<ReviewsRatings, Long> {
    List<ReviewsRatings> findAllByProductId(Long productId);
    @Query("SELECT r.product.id AS productId, AVG(r.rating) AS averageRating\n"
            + "FROM ReviewsRatings r GROUP BY r.product.id \n")
    List<IAverageRating> getAllAverageRatingProduct();
}
