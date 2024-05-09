package org.example.ecommerceweb.repository;

import org.example.ecommerceweb.domains.SkuValues;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkuValuesRepository extends JpaRepository<SkuValues, Long> {
    List<SkuValues> findAllByKey_ProductIdAndKey_OptionId(Long productId, Long optionId);

    List<SkuValues> findAllByKey_ProductId(Long productId);
}
