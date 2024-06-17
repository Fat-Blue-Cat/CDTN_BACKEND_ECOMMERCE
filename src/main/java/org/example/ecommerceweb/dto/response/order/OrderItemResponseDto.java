package org.example.ecommerceweb.dto.response.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ecommerceweb.domains.Image;
import org.example.ecommerceweb.domains.ProductSkus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDto {
    private Long id;
    private Integer quantity;
    private Double price;
    private Integer discount;
    private Double discountedPrice;
    private Long productId;
    private List<Image> images;
    private ProductSkus productSkus;
}
