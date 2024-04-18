package org.example.ecommerceweb.mapper;

import org.example.ecommerceweb.domains.Category;
import org.example.ecommerceweb.domains.Product;
import org.example.ecommerceweb.dto.response.CategoryResponseDto;
import org.example.ecommerceweb.dto.response.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Mapstruct {

//     ======================= COVERT OBJECT CATEGORY =======================
    Category mapToCategory(CategoryResponseDto categoryDto);

    @Mapping(target = "imageUrl", source = "image.imageUrl")
    CategoryResponseDto mapToCategoryResponseDto(Category category);
    List<CategoryResponseDto> mapToCategoryResponseDtoList(List<Category> categories);

//     ======================= COVERT OBJECT PRODUCT =======================
//     Product mapToProduct(ProductResponseDto productDto);
    @Mapping(target = "category", source = "product.category.name")
    @Mapping(target = "brand", source = "product.brand.name")
    @Mapping(target = "images", source = "product.images")
     ProductResponseDto mapToProductResponseDto(Product product);
//    @Mapping(target = "category", source = "category")
//    @Mapping(target = "brand", source = "brand")
//    Product createProductMaptoProduct(com.springboot.ecommercewebsite.request.CreateProductRequest createProductRequest);
}
