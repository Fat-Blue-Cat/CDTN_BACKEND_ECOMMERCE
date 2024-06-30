package org.example.ecommerceweb.repository;

import org.example.ecommerceweb.domains.OptionValues;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionValuesRepository extends JpaRepository<OptionValues, Long> {
    OptionValues findByValueAndOptionId(String name, Long optionId);
    Boolean deleteAllByOptionId(Long optionId);
    List<OptionValues> findAllByOptionId(Long optionId);
}
