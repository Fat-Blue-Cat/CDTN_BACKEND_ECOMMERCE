package org.example.ecommerceweb.domains;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "product_price_history")
public class ProductPriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price")
    private Double price;

    @Column(name = "effective_date")
    private LocalDateTime effectiveDate;

    @ManyToOne
    @JoinColumn(name = "product_skus_id")
    private ProductSkus productSkus;

}
