package org.example.ecommerceweb.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.*;
import org.example.ecommerceweb.domains.keys.KeySkuValues;
import org.example.ecommerceweb.dto.req.product.*;
import org.example.ecommerceweb.dto.response.product.ProductResponseDto;
import org.example.ecommerceweb.dto.response.projection.IAverageRating;
import org.example.ecommerceweb.exceptions.ProductException;
import org.example.ecommerceweb.mapper.Mapstruct;
import org.example.ecommerceweb.mapper.MapstructImpl;
import org.example.ecommerceweb.repository.*;
import org.example.ecommerceweb.service.CloudinaryService;
import org.example.ecommerceweb.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final CloudinaryService cloudinaryService;
    private final ImageServiceImpl imageService;
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final MapstructImpl mapstructImpl;
    private final ImageRepository imageRepository;
    private final Mapstruct mapstruct;
    private final OptionRepository optionRepository;
    private final OptionValuesRepository optionValuesRepository;
    private final ProductSkusRepository productSkusRepository;
    private final SkuValuesRepository skuValuesRepository;
    private final ProductPriceHistoryRepository productPriceHistoryRepository;
    private final ReviewsRatingsRepository reviewsRatingsRepository;

    @Override
    @Transactional
    public void createProduct(CreateProductRequest createProductRequest, MultipartFile[] multipartFiles) throws IOException {
        Set<OptionRequestDto> optionRequestDto = mapstruct.stringToSet(createProductRequest.getOptionRequestDtoList(), OptionRequestDto.class);
        Set<VariantsRequestDto> variantsRequestDto = mapstruct.stringToSet(createProductRequest.getVariantsRequestDtoList(), VariantsRequestDto.class);

        Brand brand = brandRepository.findById(createProductRequest.getBrandId())
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + createProductRequest.getBrandId()));
        Category category = categoryRepository.findById(createProductRequest.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + createProductRequest.getCategoryId()));

        Product product = Product.builder()
                .title(createProductRequest.getTitle())
                .description(createProductRequest.getDescription())
                .discountPercent(createProductRequest.getDiscountPercent())
                .totalQuantity(variantsRequestDto.stream().mapToInt(VariantsRequestDto::getQuantity).sum())
                .brand(brand)
                .category(category)
                .createAt(LocalDateTime.now())
                .build();
        Product savedProduct = productRepository.save(product);

        for (MultipartFile multipartFile : multipartFiles) {
            BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
            if (bi == null) {
                throw new IOException("Invalid image!");
            }
            Map result = cloudinaryService.upload(multipartFile);
            Image image = Image.builder()
                    .name((String) result.get("original_filename"))
                    .imageUrl((String) result.get("url"))
                    .imageId((String) result.get("public_id"))
                    .products(savedProduct)
                    .build();
            imageService.save(image);
        }

        for (OptionRequestDto option : optionRequestDto) {
            Options newOption = Options.builder()
                    .name(option.getName())
                    .product(savedProduct)
                    .build();
            Options savedOption = optionRepository.save(newOption);
            for (OptionValueRequestDto optionValueRequestDto : option.getProductOptionValues()) {
                OptionValues optionValues = OptionValues.builder()
                        .value(optionValueRequestDto.getValue())
                        .productId(savedProduct.getId())
                        .option(savedOption)
                        .build();
              OptionValues optionValuesSaved= optionValuesRepository.save(optionValues);
            }
        }

        for (VariantsRequestDto variant : variantsRequestDto) {
            ProductSkus newVariant = ProductSkus.builder()
                    .sku(variant.getSku())
                    .quantity(variant.getQuantity())
                    .price(variant.getPrice())
                    .product(savedProduct)
                    .build();
            ProductSkus savedVariant = productSkusRepository.save(newVariant);
            for (OptionSelectRequestDto option: variant.getOptionSelectRequestDtoList()) {
                Options options = optionRepository.findByNameAndProductId(option.getNameOption(),savedProduct.getId());
                OptionValues optionValues = optionValuesRepository.findByValueAndOptionId(option.getValueOption(),options.getId());
                SkuValues skuValues = SkuValues.builder()
                        .key(KeySkuValues.builder().sku(savedVariant).option(options).productId(savedProduct.getId()).build()).optionValues(optionValues).build();
                SkuValues skuValuesSaved = skuValuesRepository.save(skuValues);

            }
        }


    }


    @Transactional
    @Override
    public void updateProduct(CreateProductRequest createProductRequest, Long id, MultipartFile[] multipartFiles, Long[] imageIdDelete) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sản phẩm với id: " + id));


        Brand brand = brandRepository.findById(createProductRequest.getBrandId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thương hiệu với id: " + createProductRequest.getBrandId()));
        Category category = categoryRepository.findById(createProductRequest.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy danh mục với id: " + createProductRequest.getCategoryId()));

        Set<OptionRequestDto> optionRequestDto = mapstruct.stringToSet(createProductRequest.getOptionRequestDtoList(), OptionRequestDto.class);
        Set<VariantsRequestDto> variantsRequestDto = mapstruct.stringToSet(createProductRequest.getVariantsRequestDtoList(), VariantsRequestDto.class);

        product.setTitle(createProductRequest.getTitle());
        product.setDescription(createProductRequest.getDescription());
        product.setDiscountPercent(createProductRequest.getDiscountPercent());
        product.setTotalQuantity(variantsRequestDto.stream().mapToInt(VariantsRequestDto::getQuantity).sum());
        product.setBrand(brand);
        product.setCategory(category);
        product.setCreateAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        for (OptionRequestDto option : optionRequestDto) {
            Options currentOption = optionRepository.findById(option.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy option với id: " + option.getId()));

            currentOption.setName(option.getName());
            Options savedOption = optionRepository.save(currentOption);
            for (OptionValueRequestDto optionValueRequestDto : option.getProductOptionValues()) {
                OptionValues currentOptionValues = optionValuesRepository.findById(optionValueRequestDto.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy option value với id: " + optionValueRequestDto.getId()));
                currentOptionValues.setValue(optionValueRequestDto.getValue());
                currentOptionValues.setProductId(savedProduct.getId());
                OptionValues optionValuesSaved= optionValuesRepository.save(currentOptionValues);
            }
        }

        for (VariantsRequestDto variant : variantsRequestDto) {
            ProductSkus currentVariant = productSkusRepository.findById(variant.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy biến thể với id: " + variant.getId()));
//
            currentVariant.setSku(variant.getSku());
            currentVariant.setQuantity(variant.getQuantity());
            currentVariant.setPrice(variant.getPrice());

            ProductSkus savedVariant = productSkusRepository.save(currentVariant);

            ProductPriceHistory productPriceHistory = ProductPriceHistory.builder()
                    .price(variant.getPrice())
                    .effectiveDate(LocalDateTime.now())
                    .productSkus(savedVariant)
                    .build();
            productPriceHistoryRepository.save(productPriceHistory);
//            for (OptionSelectRequestDto option: variant.getOptionSelectRequestDtoList()) {
//                Options options = optionRepository.findByNameAndProductId(option.getNameOption(),savedProduct.getId());
//                OptionValues optionValues = optionValuesRepository.findByValueAndOptionId(option.getValueOption(),options.getId());
//                SkuValues skuValues = SkuValues.builder()
//                        .key(KeySkuValues.builder().sku(savedVariant).option(options).productId(savedProduct.getId()).build()).optionValues(optionValues).build();
//                SkuValues skuValuesSaved = skuValuesRepository.save(skuValues);
//
//            }
        }



        // Xóa các hình ảnh cũ của sản phẩm
        if(imageIdDelete == null) return;
        for (Long imageId : imageIdDelete) {
            imageService.delete(imageId.intValue());
        }
        if (multipartFiles == null) {
            return;
        }

        // Thêm các hình ảnh mới vào sản phẩm
        for (MultipartFile multipartFile : multipartFiles) {
            BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
            if (bi == null) {
                throw new IOException("Hình ảnh không hợp lệ!");
            }
            Map result = cloudinaryService.upload(multipartFile);
            Image image = Image.builder()
                    .name((String) result.get("original_filename"))
                    .imageUrl((String) result.get("url"))
                    .imageId((String) result.get("public_id"))
                    .products(savedProduct)
                    .build();
            imageService.save(image);
        }
    }



    @Transactional
    @Override
    public void deleteProduct(Long id) throws IOException {
        List<Image> images = imageRepository.findAllByProductsId(id);
        for (Image image : images) {
            imageService.delete(image.getId());
        }
        productRepository.deleteById(id);

    }

//    @Override
//    public ProductResponseDto getProduct(Long id) {
////        List<SkuValues> skuValuesList = skuValuesRepository.findAllByKey_ProductId(id);
////        List<ProductVariationsResponseDto> productVariationsResponseDtoList = mapstruct.maptoProductVariationsResponseDtoList(skuValuesList);
//
//        ProductResponseDto productResponseDto = mapstruct.mapToProductResponseDto(productRepository.findById(id).get());
////        productResponseDto.setProductVariationsResponseDtoList(productVariationsResponseDtoList);
////        return mapstructImpl.mapToProductResponseDto(productRepository.findById(id).get());
//        return productResponseDto;
//    }

    @Override
    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        // Sort options by their IDs
//        List<Options> sortedOptions = product.getOptions().stream()
//                .sorted(Comparator.comparing(Options::getId)) // Sort by type in ascending order
//                .collect(Collectors.toList());
        List<Options> sortedList = product.getOptions().stream()
                .sorted((obj1, obj2) -> obj1.getId().compareTo(obj2.getId()))
                .collect(Collectors.toList());


        // Set the sorted options back to the product
        product.setOptions(sortedList);

        // Now, map the sorted product to ProductResponseDto
        return mapstruct.mapToProductResponseDto(product);
    }


    @Override
    public Page<ProductResponseDto> getAllProduct(String title, String brandName, String categoryName, String sort, int minPrice, int maxPrice, int page, int size) {
//        return  null;
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        List<IAverageRating> IAverageRatingList = reviewsRatingsRepository.getAllAverageRatingProduct();
        Page<Product> products = productRepository.getAllProductByFilter(title, brandName, categoryName, sort, minPrice, maxPrice, pageable);
        return products.map(product -> {
            ProductResponseDto productResponseDto = mapstruct.mapToProductResponseDto(product);
            Optional<IAverageRating> averageRating = IAverageRatingList.stream().filter(average -> average.getProductId() == product.getId()).findFirst();
            averageRating.ifPresent(iAverageRating -> productResponseDto.setTotalRating(iAverageRating.getAverageRating()));
            return productResponseDto;
        });
    }

    @Override
    public List<SkuValues> getProductOption(Long id, Long optionId) throws ProductException {
        return skuValuesRepository.findAllByKey_ProductIdAndKey_OptionId(id, optionId);
    }


}
