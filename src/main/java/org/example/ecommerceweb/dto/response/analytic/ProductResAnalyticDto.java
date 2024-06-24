package org.example.ecommerceweb.dto.response.analytic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ecommerceweb.domains.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResAnalyticDto {

    private long id;

    private String title;

    private String description;

    private int discountPercent;

    private int totalQuantity;

    private List<Image> images;

    private Category category;

    private Brand brand;

    private Double totalRating;

}

