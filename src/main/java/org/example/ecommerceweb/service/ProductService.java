package org.example.ecommerceweb.service;
import org.example.ecommerceweb.domains.Product;
import org.example.ecommerceweb.domains.SkuValues;
import org.example.ecommerceweb.dto.req.product.CreateProductRequest;
import org.example.ecommerceweb.dto.response.product.ProductResponseDto;
import org.example.ecommerceweb.exceptions.ProductException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void createProduct(CreateProductRequest createProductRequest, MultipartFile[] multipartFiles) throws IOException, ProductException;

    void updateProduct(CreateProductRequest createProductRequest, Long id, MultipartFile[] multipartFiles,Long[] imageIdDelete) throws IOException,ProductException;

    void deleteProduct(Long id) throws IOException, ProductException;
    ProductResponseDto getProduct(Long id) throws ProductException;

    Page<ProductResponseDto> getAllProduct(String title, String brandName, String categoryName, String sort, int minPrice, int maxPrice , int page, int size) throws ProductException;

    List<SkuValues> getProductOption(Long id, Long optionId) throws ProductException;

}
