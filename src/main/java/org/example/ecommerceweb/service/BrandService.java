package org.example.ecommerceweb.service;

import org.example.ecommerceweb.domains.Brand;
import org.springframework.data.domain.Page;

public interface BrandService {

    Brand createBrand(String brandName);

    Brand updateBrand(String brandName, Long id);

    void deleteBrand(Long id);

    Brand getBrand(Long id);

    Page<Brand> getAllBrandsByName(String name, int page, int size);



}
