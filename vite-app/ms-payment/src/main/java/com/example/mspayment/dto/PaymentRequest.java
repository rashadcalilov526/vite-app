// PaymentRequest.java
package com.example.mspayment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PaymentRequest {
    private Double amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date checkDate;
    private Integer courseMonth;
    private Long courseId;
    private Long studentId;
    private String cardName;
}
