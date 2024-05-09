package org.example.ecommerceweb.repository;

import org.example.ecommerceweb.domains.OptionValues;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionValuesRepository extends JpaRepository<OptionValues, Long> {
    OptionValues findByValueAndOptionId(String name, Long optionId);
}
