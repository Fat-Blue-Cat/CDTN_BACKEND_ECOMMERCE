package org.example.ecommerceweb.dto.response.analytic;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IRevenue {
    BigDecimal getRevenue();
    Long getMonth();
}
