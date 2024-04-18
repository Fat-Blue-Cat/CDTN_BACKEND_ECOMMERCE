package org.example.ecommerceweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthDto {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";

}