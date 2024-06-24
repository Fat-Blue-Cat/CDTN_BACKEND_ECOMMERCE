package org.example.ecommerceweb.dto.response.analytic;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BestSaleResponseDto{
    private ProductResAnalyticDto productResAnalyticDto;
    private Long totalSale;
}
