package org.example.ecommerceweb.repository;

import org.example.ecommerceweb.domains.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupons, Long> {
    Optional<Coupons> findByCode(String code);
}
