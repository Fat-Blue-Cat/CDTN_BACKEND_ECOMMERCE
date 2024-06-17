package org.example.ecommerceweb.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VnPayIpnResponseDTO {
    @JsonProperty(value = "RspCode")
    private String rspCode;

    @JsonProperty(value = "Message")
    private String message;

    @JsonProperty(value = "OrderId")
    private Long orderId;
}