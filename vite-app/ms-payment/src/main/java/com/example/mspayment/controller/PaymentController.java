// PaymentController.java
package com.example.mspayment.controller;

import com.example.mspayment.dto.PaymentRequest;
import com.example.mspayment.dto.PaymentResponse;
import com.example.mspayment.model.Payment;
import com.example.mspayment.service.PaymentService;
import com.example.mspayment.mapper.PaymentMapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;
    @GetMapping("/payments/byCardName")
    public List<Payment> getPaymentsByCardName(@RequestParam String cardName) {
        return paymentService.getPaymentsByCardName(cardName);
    }
    @GetMapping("/byStudentId")
    public List<Payment> getPaymentsByStudentId(@RequestParam Long studentId) {
        return paymentService.getPaymentsByStudentId(studentId);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse createPayment(@RequestBody PaymentRequest paymentRequest) {
        Payment payment = paymentService.createPayment(paymentMapper.mapRequestToEntity(paymentRequest));
        return paymentMapper.mapEntityToResponse(payment);
    }
    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public List<Payment> getFilteredPayments(
            @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {

        return paymentService.getPaymentsByDateRange(from, to);
    }
    @GetMapping("/{id}")
    public PaymentResponse getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        return paymentMapper.mapEntityToResponse(payment);
    }

    @GetMapping("/all")
    public List<PaymentResponse> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return payments.stream()
                .map(paymentMapper::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @PutMapping("/update")
    public PaymentResponse updatePayment(@RequestBody PaymentResponse paymentRequest) {
        Payment payment = paymentService.updatePayment(paymentRequest.getId(), paymentMapper.mapResponseToEntity(paymentRequest));
        return paymentMapper.mapEntityToResponse(payment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
    }
}
