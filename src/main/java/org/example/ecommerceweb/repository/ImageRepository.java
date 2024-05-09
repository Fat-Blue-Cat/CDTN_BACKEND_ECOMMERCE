package org.example.ecommerceweb.repository;


import org.example.ecommerceweb.domains.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Integer> {
    List<Image> findByOrderById();

    List<Image> findAllByProductsId(Long productId);
}