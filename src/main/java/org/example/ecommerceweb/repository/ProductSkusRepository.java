package org.example.ecommerceweb.repository;

import org.example.ecommerceweb.domains.ProductSkus;
import org.example.ecommerceweb.domains.SkuValues;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductSkusRepository extends JpaRepository<ProductSkus, Long> {
    List<ProductSkus> findAllByProductId(Long productId);
}
