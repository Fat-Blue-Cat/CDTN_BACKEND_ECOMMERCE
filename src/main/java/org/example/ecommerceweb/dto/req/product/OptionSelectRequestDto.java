package org.example.ecommerceweb.dto.req.product;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class    OptionSelectRequestDto {
    private String nameOption;
    private String valueOption;
}
