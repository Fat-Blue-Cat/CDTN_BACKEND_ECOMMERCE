package org.example.ecommerceweb.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "vnpay")
@ConfigurationPropertiesScan
public class VnPayConfig {
    private String vnp_TmnCode;
    private String vnp_HashSecret;
    private String vnp_Url;
    private String vnp_Version;
    private String vnp_ReturnUrl;
    private String vnp_Locale;
    private String vnp_refundUrl;



}
