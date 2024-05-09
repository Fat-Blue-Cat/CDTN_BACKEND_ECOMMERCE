package org.example.ecommerceweb.repository;

import org.example.ecommerceweb.domains.ProductSkus;
import org.example.ecommerceweb.domains.SkuValues;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSkusRepository extends JpaRepository<ProductSkus, Long> {
}
