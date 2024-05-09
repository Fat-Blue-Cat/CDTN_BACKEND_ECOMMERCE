package org.example.ecommerceweb.dto.req.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
    private String title;
    private String description;
    private int discountPercent;
    private Long brandId;
    private Long categoryId;
    private int totalQuantity;

    private String optionRequestDtoList;

    private String variantsRequestDtoList;

}
