package org.example.ecommerceweb.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.dto.response.analytic.*;
import org.example.ecommerceweb.mapper.Mapstruct;
import org.example.ecommerceweb.repository.OrderRepository;
import org.example.ecommerceweb.repository.ProductRepository;
import org.example.ecommerceweb.service.AnalyticService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticServiceImpl implements AnalyticService {
    private final OrderRepository orderRepository;
    private final Mapstruct mapstruct;
    private final ProductRepository productRepository;

    @Override
    public AnalyticResponseDto getAnalytic() {
        Long totalOrderIn30Days = orderRepository.getAllOrderIn30Days();
        BigDecimal totalRevenueIn30Days = orderRepository.getTotalRevenueIn30Days();
        Long totalOrderCancelIn30Days = orderRepository.getTotalOrderCancelIn30Days();
        Long totalNewUserIn30Days = orderRepository.getTotalUserIn30Days();
        List<IRevenue> revenueList = orderRepository.getTotalRevenueEveryMonth();
        List<IOrderRecent> orderRecentList = orderRepository.getTotalOrderEveryMonth();
        List<IBestSaleProduct> bestSaleList = orderRepository.getBestSaleProduct();

        List<Long> productIdList = bestSaleList.stream().map(IBestSaleProduct::getProductId).toList();
        List<ProductResAnalyticDto> productResAnalyticDtos = mapstruct.mapToProductResAnalyticDtoList(productRepository.findAllById(productIdList));
        List<BestSaleResponseDto> listBestSaleResponseDto = productResAnalyticDtos.stream().map(productResAnalyticDto -> {
            IBestSaleProduct iBestSaleProduct = bestSaleList.stream().filter(bestSaleProduct -> bestSaleProduct.getProductId().equals(productResAnalyticDto.getId())).findFirst().get();
            return BestSaleResponseDto.builder().productResAnalyticDto(productResAnalyticDto)
                    .totalSale(iBestSaleProduct.getTotalSold())
                    .build();
        }).toList();


        AnalyticResponseDto analyticResponseDto = AnalyticResponseDto.builder().orderRecentList(orderRecentList)
                .revenueList(revenueList)
                .totalOrderIn30Days(totalOrderIn30Days)
                .totalRevenueIn30Days(totalRevenueIn30Days)
                .totalOrderCancelIn30Days(totalOrderCancelIn30Days)
                .totalNewUserIn30Days(totalNewUserIn30Days)
                .bestSaleList(listBestSaleResponseDto)
                .build();

        return analyticResponseDto;
    }
}
