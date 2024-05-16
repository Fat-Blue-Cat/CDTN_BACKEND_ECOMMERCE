package org.example.ecommerceweb.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.commons.Constant;
import org.example.ecommerceweb.config.VnPayConfig;
import org.example.ecommerceweb.domains.Order;
import org.example.ecommerceweb.domains.Payment;
import org.example.ecommerceweb.domains.PaymentStatus;
import org.example.ecommerceweb.dto.response.VnPayIpnResponseDTO;
import org.example.ecommerceweb.dto.response.VnPayRefundResponseDTO;
import org.example.ecommerceweb.repository.OrderRepository;
import org.example.ecommerceweb.repository.PaymentRepository;
import org.example.ecommerceweb.service.VnPayService;
import org.example.ecommerceweb.util.Hash;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VnPayServiceImpl implements VnPayService {
    private final PaymentRepository paymentRepository;
    private final VnPayConfig vnPayConfig;
    private final OrderRepository orderRepository;

    @Override
    public String getPaymentUrl(String ipAddress, BigDecimal amount, Long orderId, Long userId) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime createDate = LocalDateTime.now();
        LocalDateTime expireDate = createDate.plusMinutes(5);

        String orderInfo = "Pay for order ID: " + orderId;


        Payment payment = new Payment();

        payment.setAmount(amount);
        payment.setCreateDate(createDate);
        payment.setExpireDate(expireDate);
        payment.setOrderInfo(orderInfo);
        payment.setOrderId(orderId);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setUserId(userId);

        Payment savedPayment = paymentRepository.save(payment);


        Map<String, String> params = new TreeMap<>(String::compareTo);
        params.put("vnp_Version", vnPayConfig.getVnp_Version());
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", vnPayConfig.getVnp_TmnCode());
        params.put("vnp_Amount", amount.multiply(BigDecimal.valueOf(100))
                .stripTrailingZeros().toPlainString());
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_TxnRef", savedPayment.getId().toString());
        params.put("vnp_OrderType", "other");
        params.put("vnp_OrderInfo", orderInfo);
        params.put("vnp_ReturnUrl", vnPayConfig.getVnp_ReturnUrl());
        params.put("vnp_Locale", vnPayConfig.getVnp_Locale());
        params.put("vnp_IpAddr", ipAddress);
        params.put("vnp_CreateDate", createDate.format(format));
        params.put("vnp_ExpireDate", expireDate.format(format));

        List<String> paramList = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            try {
                paramList.add(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(),
                        StandardCharsets.US_ASCII.toString()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String paramString = String.join("&", paramList);
        String hashData = Hash.hmacSHA512(paramString, vnPayConfig.getVnp_HashSecret());
        String url = vnPayConfig.getVnp_Url() + "?" + paramString + "&vnp_SecureHash=" + hashData;

        System.out.println(url);

        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setPaymentMethod(Constant.PAYMENT_CASH);
        order.setOrderStatus(Constant.ORDER_PLACED);
        order.setPaymentStatus(Constant.PAYMENT_SUCCESS);
        orderRepository.save(order);

        return url;
    }

    @Override
    public Map<String, String> refund(Long orderId) {
        String ipAddress = "0:0:0:0:0:0:0:1";

        Payment payment = paymentRepository.findByOrderIdAndPaymentStatus(orderId,
                PaymentStatus.SUCCESS).orElseThrow();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime createDate = LocalDateTime.now();

        Map<String, String> params = new LinkedHashMap<>();

        params.put("vnp_RequestId", UUID.randomUUID().toString().replace("-", ""));
        params.put("vnp_Version", vnPayConfig.getVnp_Version());
        params.put("vnp_Command", "refund");
        params.put("vnp_TmnCode", vnPayConfig.getVnp_TmnCode());
        params.put("vnp_TransactionType", "02");
        params.put("vnp_TxnRef", payment.getId().toString());
        params.put("vnp_Amount", payment.getAmount().multiply(BigDecimal.valueOf(100)).
                stripTrailingZeros().toPlainString());
        params.put("vnp_TransactionNo", "");
        params.put("vnp_TransactionDate", payment.getCreateDate().format(format));
        params.put("vnp_CreateBy", "User");
        params.put("vnp_CreateDate", createDate.format(format));
        params.put("vnp_IpAddr", ipAddress);
        params.put("vnp_OrderInfo", "Refund for order ID:" + orderId);

        Collection<String> values = params.values();
        String data = String.join("|", values);

        String hash = Hash.hmacSHA512(data, vnPayConfig.getVnp_HashSecret());

        params.put("vnp_SecureHash", hash);

        RestTemplate restTemplate = new RestTemplate();
        VnPayRefundResponseDTO response = restTemplate.postForObject(vnPayConfig.getVnp_refundUrl(),
                params, VnPayRefundResponseDTO.class);
        paymentRepository.save(payment);

        if (response.getResponseCode().equals("00")) {
            payment.setPaymentStatus(PaymentStatus.REFUNDED);
//            kafkaTemplate.send("refund-result", new RefundedEvent(bookingId, true));
        } else {
            payment.setPaymentStatus(PaymentStatus.REFUND_FAILED);
//            kafkaTemplate.send("refund-result", new RefundedEvent(bookingId, false));
        }

        return params;
    }

    @Override
    public Map<String, String> refundForCancellingOrder(Long orderId) {
        String ipAddress = "0:0:0:0:0:0:0:1";


        Payment payment = paymentRepository.findByOrderIdAndPaymentStatus(orderId,
                PaymentStatus.SUCCESS).orElseThrow();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime createDate = LocalDateTime.now();

        Map<String, String> params = new LinkedHashMap<>();

        params.put("vnp_RequestId", Hash.getRandomNumber(8).toString());
        params.put("vnp_Version", vnPayConfig.getVnp_Version());
        params.put("vnp_Command", "refund");
        params.put("vnp_TmnCode", vnPayConfig.getVnp_TmnCode());
        params.put("vnp_TransactionType", "02");
        params.put("vnp_TxnRef", payment.getOrderId().toString());
        params.put("vnp_Amount", payment.getAmount().multiply(BigDecimal.valueOf(100))
                .stripTrailingZeros().toPlainString());
        params.put("vnp_TransactionNo", "");
        params.put("vnp_TransactionDate", payment.getCreateDate().format(format));
        params.put("vnp_CreateBy", "User");
        params.put("vnp_CreateDate", createDate.format(format));
        params.put("vnp_IpAddr", ipAddress);
        params.put("vnp_OrderInfo", "Refund for order ID:" + orderId);

        Collection<String> values = params.values();
        String data = String.join("|", values);


        String hash = Hash.hmacSHA512(data.toString(), vnPayConfig.getVnp_HashSecret());

        params.put("vnp_SecureHash", hash);

        RestTemplate restTemplate = new RestTemplate();
        VnPayRefundResponseDTO response = restTemplate.postForObject(vnPayConfig.getVnp_refundUrl(),
                params, VnPayRefundResponseDTO.class);

            if (response.getResponseCode().equals("00")) {
            payment.setPaymentStatus(PaymentStatus.REFUNDED);
//            kafkaTemplate.send("refund-for-cancelling-result",
//                    new RefundResultForCancellingEvent(bookingId, true));
        } else {
            payment.setPaymentStatus(PaymentStatus.REFUND_FAILED);
//            kafkaTemplate.send("refund-for-cancelling-result",
//                    new RefundResultForCancellingEvent(bookingId, false));
        }

        paymentRepository.save(payment);
        return params;
    }

    @Override
    public VnPayIpnResponseDTO handleIpn(Map<String, String> fields) {
        String secureHash = fields.get("vnp_SecureHash");

        fields.remove("vnp_SecureHashType");
        fields.remove("vnp_SecureHash");

        Map<String, String> fieldEncode = fields.entrySet().stream().collect(Collectors.toMap(entry -> {
            try {
                return URLEncoder.encode(entry.getKey(), StandardCharsets.US_ASCII.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return entry.getKey();
        }, entry -> {
            try {
                return URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return entry.getKey();
        }));

        String hash = Hash.hashAllFields(fieldEncode, vnPayConfig.getVnp_HashSecret());

        VnPayIpnResponseDTO vnPayIpnResponseDTO = null;
        String txnRef = fields.get("vnp_TxnRef");
        Payment payment = paymentRepository.findById(Long.valueOf(Integer.valueOf(txnRef))).orElse(null);
        if (hash.equals(secureHash)) {

            if (Objects.nonNull(payment)) {
                String amount = fields.get("vnp_Amount");
                amount = amount.substring(0, amount.length() - 2);

                if (payment.getAmount().toBigInteger().toString().equals(amount)) {
                    if (payment.getPaymentStatus().equals(PaymentStatus.PENDING)) {
                        if (fields.get("vnp_ResponseCode").equals("00")) {
                            System.out.println("SUCCESS");
                            payment.setPaymentStatus(PaymentStatus.SUCCESS);
                        } else {
                            System.out.println("FAILED");
                            payment.setPaymentStatus(PaymentStatus.FAILED);
                        }
                        System.out.println("Success");
                        vnPayIpnResponseDTO = new VnPayIpnResponseDTO("00", "Success");
                    } else {
                        System.out.println("Order already confirmed");
                        payment.setPaymentStatus(PaymentStatus.FAILED);
                        vnPayIpnResponseDTO = new VnPayIpnResponseDTO("02", "Order already confirmed");
                    }
                } else {
                    //Số tiền không trùng khớp
                    System.out.println("Invalid amount");
                    payment.setPaymentStatus(PaymentStatus.FAILED);
                    vnPayIpnResponseDTO = new VnPayIpnResponseDTO("04", "Invalid amount");
                }
            } else {
                //Mã giao dịch không tồn tại
                System.out.println("Order not found");
                payment.setPaymentStatus(PaymentStatus.FAILED);
                vnPayIpnResponseDTO = new VnPayIpnResponseDTO("01", "Order not found");
            }

        } else {
            // Sai checksum
            System.out.println("Invalid signature");
            payment.setPaymentStatus(PaymentStatus.FAILED);
            vnPayIpnResponseDTO = new VnPayIpnResponseDTO("97", "Invalid signature");
        }

        paymentRepository.save(payment);

//        if (Objects.nonNull(payment)) {
//            if (payment.getPaymentStatus().equals(PaymentStatus.SUCCESS)) {
//                kafkaTemplate.send("payment-result", new PaymentCompletedEvent(true, payment.getBookingId()));
//            } else {
//                kafkaTemplate.send("payment-result", new PaymentCompletedEvent(false, payment.getBookingId()));
//            }
//        }

        return vnPayIpnResponseDTO;
    }
}
