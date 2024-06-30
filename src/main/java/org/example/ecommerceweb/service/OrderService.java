package org.example.ecommerceweb.service;

import org.example.ecommerceweb.domains.Address;
import org.example.ecommerceweb.domains.Order;
import org.example.ecommerceweb.domains.User;
import org.example.ecommerceweb.dto.response.order.OrderItemResponseDto;
import org.example.ecommerceweb.dto.response.order.OrderResponseDto;
import org.example.ecommerceweb.exceptions.OrderException;

import java.util.List;

public interface OrderService {
    public Order createOrder(User user, Address address, Long couponId) throws OrderException;

    public Object findOrderById(Long orderId) throws OrderException;

    public List<OrderResponseDto> usersOrderHistory(Long userId,List<String> filter);

    public Order placedOrder(Long orderId) throws OrderException;

    public Order confirmedOrder(Long orderId) throws OrderException;

    public Order shippedOrder(Long orderId) throws OrderException;

    public Order deliveredOrder(Long orderId) throws OrderException;

    public Order canceledOrder(Long orderId) throws  OrderException;

    public List<Order> getAllOrders();

    public Order updateOrderStatus(Long orderId, String status) throws OrderException;

    public void deleteOrder(Long orderId) throws OrderException;
}
