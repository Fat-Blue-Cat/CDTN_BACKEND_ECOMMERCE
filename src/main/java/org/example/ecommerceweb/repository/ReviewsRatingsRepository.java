package org.example.ecommerceweb.repository;

import org.example.ecommerceweb.domains.ReviewsRatings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRatingsRepository extends JpaRepository<ReviewsRatings, Long> {
    List<ReviewsRatings> findAllByProductId(Long productId);
}
