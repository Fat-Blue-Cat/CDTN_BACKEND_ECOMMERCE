package org.example.ecommerceweb.service;

import org.example.ecommerceweb.dto.response.CategoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CategoryService {
    void createCategory(MultipartFile multipartFile, String name) throws IOException;
    void updateCategory(Long id,MultipartFile multipartFile, String name);
    void deleteCategory(Long id);
    CategoryResponseDto getCategory(Long id);
    Page<CategoryResponseDto> getAllCategories(String name, int page, int size);
}
