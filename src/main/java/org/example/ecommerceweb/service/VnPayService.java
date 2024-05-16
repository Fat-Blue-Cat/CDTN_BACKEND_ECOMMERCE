package org.example.ecommerceweb.service;

import org.example.ecommerceweb.dto.response.VnPayIpnResponseDTO;

import java.math.BigDecimal;
import java.util.Map;

public interface VnPayService {
    String getPaymentUrl(String ipAddress, BigDecimal amount, Long orderId, Long userId);

    Map<String, String> refund(Long orderId);

    Map<String, String> refundForCancellingOrder(Long orderId);

    VnPayIpnResponseDTO handleIpn(Map<String, String> params);
}
