package org.example.ecommerceweb.repository;

import org.example.ecommerceweb.domains.Options;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Options, Long> {
    Options findByNameAndProductId(String name, Long productId);
    Boolean deleteAllByProductId(Long productId);
    List<Options> findAllByProductId(Long productId);
}
