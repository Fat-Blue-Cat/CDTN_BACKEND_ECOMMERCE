package org.example.ecommerceweb.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "coupons")
public class Coupons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "discount_description")
    private String discountDescription;

    @Column(name = "discount_value")
    private double discountValue;

    @Column(name = "times_used")
    private int timesUsed;

    @Column(name = "max_times_use")
    private int maxTimesUse;

    @Column(name = "coupon_start_date")
    private LocalDateTime couponStartDate;

    @Column(name = "coupon_end_date")
    private LocalDateTime couponEndDate;

    @Column(name="created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "coupons")
    private Set<Order> orders;
}
