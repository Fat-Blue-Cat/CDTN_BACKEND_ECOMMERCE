package org.example.ecommerceweb.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.Category;
import org.example.ecommerceweb.domains.Image;
import org.example.ecommerceweb.dto.response.CategoryResponseDto;
import org.example.ecommerceweb.mapper.Mapstruct;
import org.example.ecommerceweb.repository.CategoryRepository;
import org.example.ecommerceweb.service.CategoryService;
import org.example.ecommerceweb.service.CloudinaryService;
import org.example.ecommerceweb.service.ImageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CloudinaryService cloudinaryService;
    private final ImageService imageService;
    private final CategoryRepository categoryRepository;
    private final Mapstruct mapstruct;

    @Override
    public void createCategory(MultipartFile multipartFile, String name) throws IOException {
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if (bi == null) {
            throw new IOException("Image non valide!");
        }
        Map result = cloudinaryService.upload(multipartFile);

        Image image = Image.builder().name((String) result.get("original_filename"))
                .imageUrl((String) result.get("url"))
                .imageId((String) result.get("public_id")).build();
        imageService.save(image);

        Category category = Category.builder().name(name).image(image).build();
        categoryRepository.save(category);


    }

    @Override
    public void updateCategory(Long id, MultipartFile multipartFile, String name) {
        Category category = categoryRepository.findById(id).get();
        Optional<Image> imageOptional = imageService.getOne(category.getImage().getId());
        if (imageOptional.isEmpty()) {
            throw new RuntimeException("Image doesn't exist!");
        }
        Image image = imageOptional.get();
        String cloudinaryImageId = image.getImageId();

        if (multipartFile != null ) {
            try {
                cloudinaryService.delete(cloudinaryImageId);
                Map result = cloudinaryService.upload(multipartFile);
                image.setName((String) result.get("original_filename"));
                image.setImageUrl((String) result.get("url"));
                image.setImageId((String) result.get("public_id"));
                imageService.save(image);
            } catch (IOException e) {
                throw new RuntimeException("Failed to update image in Cloudinary", e);
            }
            category.setName(name);
            categoryRepository.save(category);
        }else{
            category.setName(name);
            category.setImage(image);
            categoryRepository.save(category);
        }


    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).get();
        Optional<Image> imageOptional = imageService.getOne(category.getImage().getId());
        if (imageOptional.isEmpty()) {
            throw new RuntimeException("Image don't exist!");
        }
        Image image = imageOptional.get();
        String cloudinaryImageId = image.getImageId();
        try {
            cloudinaryService.delete(cloudinaryImageId);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image from Cloudinary");
        }

        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryResponseDto getCategory(Long id) {
      return mapstruct.mapToCategoryResponseDto(categoryRepository.findById(id).get());
    }

    @Override
    public Page<CategoryResponseDto> getAllCategories(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categories = categoryRepository.findByName(name, pageable);
        return new PageImpl<>(categories.getContent(), pageable, categories.getTotalElements())
                .map(mapstruct::mapToCategoryResponseDto);
    }
}
