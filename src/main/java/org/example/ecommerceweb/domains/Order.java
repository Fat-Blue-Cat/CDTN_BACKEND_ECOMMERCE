package org.example.ecommerceweb.domains;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="total_item")
    private Integer TotalItem;

    @Column(name="total_price")
    private Double TotalPrice;

    @Column(name="total_discount")
    private Double TotalDiscount;

    @Column(name="total_discouted_price")
    private Double TotalDiscountedPrice;

    @Column(name="order_date")
    @Temporal(TemporalType.DATE)
    private LocalDate orderDate;

    @Column(name="order_status")
    private String orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
