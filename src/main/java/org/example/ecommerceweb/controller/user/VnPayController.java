package org.example.ecommerceweb.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.dto.response.VnPayIpnResponseDTO;
import org.example.ecommerceweb.service.VnPayService;
import org.example.ecommerceweb.util.Hash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vnpay")
public class VnPayController {
    private final VnPayService vnPayService;

    @Value("${url_FrontEnd}")
    private  String urlFrontEnd;

    @GetMapping("/payment/url")
    public String getPaymentUrl(HttpServletRequest request,
                                @RequestParam BigDecimal amount,
                                @RequestParam Long orderId,
                                @RequestParam Long userId) {

        return vnPayService.getPaymentUrl(Hash.getIpAddress(request), amount, orderId, userId);
    }

    @GetMapping("/payment/url/ipn")
    public RedirectView ipnUrlHandle(@RequestParam Map<String, String> params) {

//        return vnPayService.handleIpn(params);
        VnPayIpnResponseDTO response = vnPayService.handleIpn(params);

        String status = response.getRspCode().equals("00") ? "success" : "failure";
        String redirectUrl = urlFrontEnd+ "/payment-result?status=" + status + "&message=" + response.getMessage();

        return new RedirectView(redirectUrl);
    }

    @GetMapping("/refund")
    public Map<String, String> refund(@RequestParam Long orderId) {
        return vnPayService.refundForCancellingOrder(orderId);
    }
}
