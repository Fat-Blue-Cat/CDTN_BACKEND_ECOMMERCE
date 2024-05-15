package org.example.ecommerceweb.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddItemRequestDto {
    private Long productSkuId;
    private int quantity;
//    private Double price;
}
