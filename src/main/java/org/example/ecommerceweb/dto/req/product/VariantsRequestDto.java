package org.example.ecommerceweb.dto.req.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VariantsRequestDto {
    private Long id;
    private String sku;
    private int quantity;
    private Double price;
    private List<OptionSelectRequestDto> optionSelectRequestDtoList;
}
