package org.example.ecommerceweb.dto.response.product;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.example.ecommerceweb.domains.OptionValues;
import org.example.ecommerceweb.domains.Options;
import org.example.ecommerceweb.domains.ProductSkus;
import org.example.ecommerceweb.domains.keys.KeySkuValues;

@Getter
@Setter
public class SkuValuesResponseDto {

    private OptionResponseDto option;

    private Long productId;

    private OptionValues optionValues;
}
