package org.example.ecommerceweb.domains;


import jakarta.persistence.*;
import lombok.*;
import org.example.ecommerceweb.domains.keys.KeySkuValues;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "sku_values")
public class SkuValues {
    @EmbeddedId
    private KeySkuValues key;

    @ManyToOne
    @JoinColumn(name = "option_value_id")
    private OptionValues optionValues;



}
