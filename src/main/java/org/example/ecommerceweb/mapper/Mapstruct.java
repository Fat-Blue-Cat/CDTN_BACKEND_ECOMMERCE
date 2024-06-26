package org.example.ecommerceweb.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ecommerceweb.domains.*;
import org.example.ecommerceweb.dto.response.CategoryResponseDto;
import org.example.ecommerceweb.dto.response.UserResponseDto;
import org.example.ecommerceweb.dto.response.analytic.ProductResAnalyticDto;
import org.example.ecommerceweb.dto.response.order.OrderItemResponseDto;
import org.example.ecommerceweb.dto.response.order.OrderResponseDto;
import org.example.ecommerceweb.dto.response.product.OptionResponseDto;
import org.example.ecommerceweb.dto.response.product.ProductResponseDto;
import org.example.ecommerceweb.dto.response.product.SkuValuesResponseDto;
import org.example.ecommerceweb.dto.response.reviewsAndRatings.ReviewsRatingResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface Mapstruct {

//     ======================= COVERT OBJECT CATEGORY =======================
    Category mapToCategory(CategoryResponseDto categoryDto);

    @Mapping(target = "imageUrl", source = "image.imageUrl")
    CategoryResponseDto mapToCategoryResponseDto(Category category);
    List<CategoryResponseDto> mapToCategoryResponseDtoList(List<Category> categories);

//     ======================= COVERT OBJECT PRODUCT =======================
    default <T> Set<T> stringToSet(String json, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(Set.class, clazz);
        return objectMapper.readValue(json, type);
    }

//     ======================= COVERT OBJECT PRODUCT =======================
//     Product mapToProduct(ProductResponseDto productDto);
    ProductResponseDto mapToProductResponseDto(Product product);
    List<ProductResponseDto> mapToProductResponseDtoList(List<Product> products);

    OptionResponseDto mapToOptionResponseDto(Options options);
    List<OptionResponseDto> mapToOptionResponseDtoList(List<Options> options);


    @Mapping(target="option", source="skuValues.key.option")
    @Mapping(target="productId", source="skuValues.key.productId")
    SkuValuesResponseDto maptoSkuValuesResponseDto(SkuValues skuValues);
    List<SkuValuesResponseDto> maptoSkuValuesResponseDtoList(List<SkuValues> skuValues);


//     ======================= COVERT OBJECT USER =======================
     UserResponseDto mapToUserResponseDto(User user);
    List<UserResponseDto> mapToUserResponseDtoList(List<User> users);

//     ======================= COVERT OBJECT REVIEWSRATINS =======================
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "user", source = "user")
     ReviewsRatingResponseDto mapToReviewsRatingResponseDto(ReviewsRatings reviewsRatings);
    List<ReviewsRatingResponseDto> mapToReviewsRatingResponseDtoList(List<ReviewsRatings> reviewsRatings);

//     ======================= COVERT OBJECT ORDERITEM =======================
    @Mapping(target = "productId", source = "orderItem.productSkus.product.id")
    @Mapping(target = "images", source = "orderItem.productSkus.product.images")
    @Mapping(target = "title", source = "orderItem.productSkus.product.title")
     OrderItemResponseDto mapToOrderItemResponseDto(OrderItem orderItem);
    List<OrderItemResponseDto> mapToOrderItemResponseDtoList(List<OrderItem> orderItems);
//     ======================= COVERT OBJECT Order =======================
    OrderResponseDto mapToOrderResponseDto(Order order);
    List<OrderResponseDto> mapToOrderResponseDtoList(List<Order> orders);

//     ======================= COVERT OBJECT Order =======================
    ProductResAnalyticDto mapToProductResAnalyticDto(Product product);
    List<ProductResAnalyticDto> mapToProductResAnalyticDtoList(List<Product> products);



}
