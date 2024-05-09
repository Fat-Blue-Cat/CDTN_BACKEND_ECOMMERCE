package org.example.ecommerceweb.dto.response.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.ecommerceweb.domains.Product;
import org.example.ecommerceweb.domains.SkuValues;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ProductSkuResponseDto {
    private Long id;

    private String sku;

    private double price;

    private int quantity;

    private List<SkuValuesResponseDto> skuValues;
}
