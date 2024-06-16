package org.example.ecommerceweb.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "product_skus")
public class ProductSkus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;


    @Column(name = "sku")
    private String sku;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private int quantity;

    @OneToMany(mappedBy = "key.sku", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SkuValues> skuValues;

    @OneToMany
    @JsonIgnore
    private Set<CartItem> cartItems = new HashSet<>();

    @OneToMany(mappedBy = "productSkus")
    private List<ProductPriceHistory> productPriceHistories;


}
