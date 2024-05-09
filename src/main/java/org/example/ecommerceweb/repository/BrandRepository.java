package org.example.ecommerceweb.repository;

import org.example.ecommerceweb.domains.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query("SELECT b FROM Brand b WHERE b.name LIKE %:name%")
    Page<Brand> findByName(String name, Pageable pageable);
}
