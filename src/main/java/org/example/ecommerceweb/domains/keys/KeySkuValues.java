package org.example.ecommerceweb.domains.keys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.*;
import lombok.*;
import org.example.ecommerceweb.domains.OptionValues;
import org.example.ecommerceweb.domains.Options;
import org.example.ecommerceweb.domains.Product;
import org.example.ecommerceweb.domains.ProductSkus;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class KeySkuValues implements Serializable {
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "product_sku_id", referencedColumnName = "id")
    })
    @JsonIgnore
    private ProductSkus sku;

//    @ManyToOne
//    @JoinColumns({
//            @JoinColumn(name = "option_id", referencedColumnName = "id"),
//    })
//    private Options options;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "option_id", referencedColumnName = "id")
    })
    private Options option;

    @Column(name = "product_id")
    private Long productId;



}
