package org.example.ecommerceweb.repository;

import org.example.ecommerceweb.domains.Options;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Options, Long> {
    Options findByNameAndProductId(String name, Long productId);
}
