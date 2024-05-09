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
public class OptionRequestDto {
    private Long id;
    private String name;
    private List<OptionValueRequestDto> productOptionValues;
}
