package org.example.ecommerceweb.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.commons.Constant;
import org.example.ecommerceweb.domains.*;
import org.example.ecommerceweb.exceptions.OrderException;
import org.example.ecommerceweb.repository.*;
import org.example.ecommerceweb.service.CartService;
import org.example.ecommerceweb.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final CartService cartService;
    private final OderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final ProductSkusRepository productSkusRepository;

    @Override
    public Order createOrder(User user, Address shippAddress) {
        Cart cart = cartService.findUserCart(user.getId());
        Set<OrderItem> orderItems = new HashSet<>();

        for (CartItem item: cart.getCartItems()){
            OrderItem orderItem = OrderItem.builder().price(item.getPrice())
                    .quantity(item.getQuantity())
                    .discount(item.getDiscount())
                    .discountedPrice(item.getDiscountedPrice())
                    .productSkus(item.getProductSkus())
                    .build();



            OrderItem createdOrderItem = orderItemRepository.save(orderItem);

            orderItems.add(createdOrderItem);

            ProductSkus productSkus = productSkusRepository.findById(item.getProductSkus().getId()).orElseThrow(() -> new RuntimeException("ProductSkus not found"));
            productSkus.setQuantity(productSkus.getQuantity() - item.getQuantity());

            Product product = productRepository.findById(productSkus.getProduct().getId()).orElseThrow(() -> new RuntimeException("Product not found"));
            product.setTotalQuantity(product.getTotalQuantity() - item.getQuantity());
            productSkusRepository.save(productSkus);
            productRepository.save(product);
        }

        Order createOrder = Order.builder()
                .user(user)
                .orderItems(orderItems)
                .orderStatus(Constant.ORDER_PENDING)
                .orderDate(LocalDate.now())
                .TotalDiscount(cart.getTotalDiscount())
                .TotalPrice(cart.getTotalPrice())
                .TotalItem(cart.getTotalItem())
                .address(shippAddress)
                .TotalDiscountedPrice(cart.getTotalDiscountedPrice())
                .build();


        Order savedOrder = orderRepository.save(createOrder);

        for(OrderItem item: orderItems){
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }

        cartService.clearCart(user.getId());
        return savedOrder;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> opt= orderRepository.findById(orderId);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new OrderException("order not exist with id " + orderId);
    }


    @Override
    public List<Order> usersOrderHistory(Long userId) {
        List<Order> orders = orderRepository.getUserOrders(userId);
        return orders;
    }

    @Override
    public Order placedOrder(Long orderId)  {
        Order order = orderRepository.findById(orderId).get();
        order.setOrderStatus(Constant.ORDER_PLACED);
//        order.getPaymentDetails().setStatus("COMPLETED");
        orderRepository.save(order);
        return order;
    }



    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(Constant.ORDER_CONFIRMED);

        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(Constant.ORDER_SHIPPED);

        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(Constant.ORDER_DELIVERED);

        return orderRepository.save(order);
    }

    @Override
    public Order canceledOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        if(!order.getOrderStatus().equals(Constant.ORDER_CONFIRMED) && !order.getOrderStatus().equals(Constant.ORDER_PENDING)){
            throw new OrderException("Order can't cancel, it's already confirmed");
        }
        for (OrderItem item: order.getOrderItems()){
            ProductSkus productSkus = productSkusRepository.findById(item.getProductSkus().getId()).orElseThrow(() -> new RuntimeException("ProductSkus not found"));
            productSkus.setQuantity(productSkus.getQuantity() + item.getQuantity());

            Product product = productRepository.findById(productSkus.getProduct().getId()).orElseThrow(() -> new RuntimeException("Product not found"));
            product.setTotalQuantity(product.getTotalQuantity() + item.getQuantity());
            productSkusRepository.save(productSkus);
            productRepository.save(product);
        }
        order.setOrderStatus(Constant.ORDER_CANCELLED);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        orderRepository.delete(order);

    }
}
