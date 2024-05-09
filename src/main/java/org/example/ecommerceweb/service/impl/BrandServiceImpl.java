package org.example.ecommerceweb.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.Brand;
import org.example.ecommerceweb.repository.BrandRepository;
import org.example.ecommerceweb.service.BrandService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public Brand createBrand(String brandName) {
        Brand brand = Brand.builder().name(brandName).build();
        return brandRepository.save(brand);
    }

    @Override
    public Brand updateBrand(String brandName, Long id) {
        Brand brandResult = brandRepository.findById(id).get();
        brandResult.setName(brandName);
        return brandRepository.save(brandResult);
    }

    @Override
    public void deleteBrand(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new IllegalArgumentException("Brand with id " + id + " does not exist");
        }
        brandRepository.deleteById(id);
        if (brandRepository.existsById(id)) {
            throw new IllegalStateException("Failed to delete brand with id " + id);
        }
    }

    @Override
    public Brand getBrand(Long id) {
        return brandRepository.findById(id).get();
    }

    @Override
    public Page<Brand> getAllBrandsByName(String name, int page, int size) {
        return brandRepository.findByName(name, PageRequest.of(page, size));

    }

}
