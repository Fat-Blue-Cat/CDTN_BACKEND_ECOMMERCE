package org.example.ecommerceweb.dto.req.product;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptionValueRequestDto {
    private Long id;
    private String value;
}
