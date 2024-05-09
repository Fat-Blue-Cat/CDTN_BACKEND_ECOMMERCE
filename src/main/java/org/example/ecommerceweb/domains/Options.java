package org.example.ecommerceweb.domains;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "options")
public class Options {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id", insertable = true, updatable = true)
    private Product product;

    @Column(name = "product_id", insertable = false, updatable = false)
    private Long productId;
//
//    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL)
//    private List<OptionValues> optionValues;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OptionValues> optionValues;

    @JsonIgnore
    @OneToMany(mappedBy = "key.option", cascade = CascadeType.ALL)
    private List<SkuValues> skuValuesList;


}
