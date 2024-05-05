package org.example.ecommerceweb.domains;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;


    @Column(name = "discount_percent")
    private int discountPercent;

    @Column(name = "total_quantity")
    private int totalQuantity;


    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createAt;


    @OneToMany(mappedBy = "products")
    private List<Image> images;


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "category_id")
    private Category category;


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "brand_id")
    private Brand brand;

//    @JsonIgnore
//    @OneToMany(mappedBy = "product")
//    private Set<CartItem> cartItems = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ReviewsRatings> reviewsRatings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Set<Options> options = new HashSet<>();

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ProductSkus> productSkus = new HashSet<>();


}
