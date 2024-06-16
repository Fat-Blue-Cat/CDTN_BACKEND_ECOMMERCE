package org.example.ecommerceweb.repository;

import org.example.ecommerceweb.domains.ProductPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPriceHistoryRepository extends JpaRepository<ProductPriceHistory, Long>{
}
