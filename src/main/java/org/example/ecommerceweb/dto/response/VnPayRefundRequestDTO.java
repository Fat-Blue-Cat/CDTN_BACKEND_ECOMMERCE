package org.example.ecommerceweb.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VnPayRefundRequestDTO {
    @JsonProperty(value = "vnp_RequestId")
    private String requestId;

    @JsonProperty(value = "vnp_Version")
    private String version;

    @JsonProperty(value = "vnp_Command")
    private String command;

    @JsonProperty(value = "vnp_TmnCode")
    private String tmnCode;

    @JsonProperty(value = "vnp_TransactionType")
    private String transactionType;

    @JsonProperty(value = "vnp_TxnRef")
    private String txnRef;

    @JsonProperty(value = "vnp_Amount")
    private String amount;

    @JsonProperty(value = "vnp_OrderInfo")
    private String orderInfo;

    @JsonProperty(value = "vnp_TransactionDate")
    private String transactionDate;

    @JsonProperty(value = "vnp_CreateBy")
    private String createBy;

    @JsonProperty(value = "vnp_CreateDate")
    private String createDate;

    @JsonProperty(value = "vnp_IpAddr")
    private String ipAddr;

    @JsonProperty(value = "vnp_SecureHash")
    private String secureHash;
}
