package org.example.ecommerceweb.domains.keys;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class KeySkuValues implements Serializable {
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "product_sku_id", referencedColumnName = "id")
    })
    private ProductSkus sku;

//    @ManyToOne
//    @JoinColumns({
//            @JoinColumn(name = "option_id", referencedColumnName = "id"),
//    })
//    private Options options;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
            @JoinColumn(name = "option_id", referencedColumnName = "id")
    })
    private Options option;



}
