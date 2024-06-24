package org.example.ecommerceweb.dto.response.analytic;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticResponseDto {
    private Long totalOrderIn30Days;
    private BigDecimal totalRevenueIn30Days;
    private Long totalOrderCancelIn30Days;
    private Long totalNewUserIn30Days;
    private List<IOrderRecent> orderRecentList;
    private List<IRevenue> revenueList;
    private List<BestSaleResponseDto> bestSaleList;

}
