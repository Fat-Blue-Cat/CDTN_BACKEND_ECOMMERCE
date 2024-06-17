package org.example.ecommerceweb.dto.response.order;

import lombok.*;
import org.example.ecommerceweb.domains.Address;
import org.example.ecommerceweb.domains.Coupons;
import org.example.ecommerceweb.domains.OrderItem;
import org.example.ecommerceweb.domains.User;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;
    private Integer totalItem;
    private Double totalPrice;
    private Double totalDiscountedPrice;
    private LocalDate orderDate;
    private String orderStatus;
    private String paymentStatus;
    private String paymentMethod;
    private User user;
    private Set<OrderItemResponseDto> orderItems;
    private Address address;
    private Coupons coupons;
}
