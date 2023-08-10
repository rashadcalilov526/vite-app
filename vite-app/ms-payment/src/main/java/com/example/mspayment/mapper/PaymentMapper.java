// PaymentMapper.java
package com.example.mspayment.mapper;

import com.example.mspayment.dto.PaymentRequest;
import com.example.mspayment.dto.PaymentResponse;
import com.example.mspayment.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentResponse mapEntityToResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getAmount(),
                payment.getCheckDate(),
                payment.getCourseMonth(),
                payment.getCourseId(),
                payment.getStudentId(),
                payment.getCardName()
        );
    }
public Payment mapResponseToEntity(PaymentResponse paymentRequest){
    return Payment.builder()
            .id(paymentRequest.getId())
            .amount(paymentRequest.getAmount())
            .checkDate(paymentRequest.getCheckDate())
            .courseMonth(paymentRequest.getCourseMonth())
            .courseId(paymentRequest.getCourseId())
            .studentId(paymentRequest.getStudentId())
            .cardName(paymentRequest.getCardName())
            .build();
}

    public Payment mapRequestToEntity(PaymentRequest paymentRequest) {
        return Payment.builder()
                .amount(paymentRequest.getAmount())
                .checkDate(paymentRequest.getCheckDate())
                .courseMonth(paymentRequest.getCourseMonth())
                .courseId(paymentRequest.getCourseId())
                .studentId(paymentRequest.getStudentId())
                .cardName(paymentRequest.getCardName())
                .build();
    }
}
