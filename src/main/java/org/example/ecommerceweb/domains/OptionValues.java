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
@Table(name = "option_values")
public class OptionValues {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonIgnore
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "option_id", referencedColumnName = "id")
    })
    private Options option;

    @Column(name = "product_id")
    private Long productId;
//
//    @Column(name="product_id")
//    private Long productId;


    @Column(name = "value")
    private String value;

    @JsonIgnore
    @OneToMany(mappedBy = "optionValues", cascade = CascadeType.ALL)
    private List<SkuValues> skuValuesList;



}
