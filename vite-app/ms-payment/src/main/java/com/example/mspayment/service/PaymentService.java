// PaymentService.java
package com.example.mspayment.service;

import com.example.mspayment.model.Payment;
import com.example.mspayment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(Payment payment) {
        // Add any additional business logic before saving the payment entity
        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsByCardName(String cardName) {
        return paymentRepository.findPaymentsByCardName(cardName);
    }
    public Payment getPaymentById(Long id) {
        // Add any additional business logic for fetching a payment by ID
        return paymentRepository.findById(id).orElse(null);
    }
    public List<Payment> getPaymentsByDateRange(Date from, Date to) {
        return paymentRepository.findPaymentsByDateRange(from, to);
    }
    public List<Payment> getAllPayments() {
        // Add any additional business logic for fetching all payments
        return paymentRepository.findAll();
    }
    public List<Payment> getPaymentsByStudentId(Long studentId) {
        return paymentRepository.findPaymentsByStudentId(studentId);
    }
    public Payment updatePayment(Long id, Payment updatedPayment) {
        Optional<Payment> existingPaymentOptional = paymentRepository.findById(id);
        if (existingPaymentOptional.isPresent()) {
            Payment existingPayment = existingPaymentOptional.get();
            // Update the fields of the existing payment entity
            existingPayment.setAmount(updatedPayment.getAmount());
            existingPayment.setCheckDate(updatedPayment.getCheckDate());
            existingPayment.setCourseMonth(updatedPayment.getCourseMonth());
            existingPayment.setCourseId(updatedPayment.getCourseId());
            existingPayment.setStudentId(updatedPayment.getStudentId());
            existingPayment.setCardName(updatedPayment.getCardName());
            // Save the updated entity
            return paymentRepository.save(existingPayment);
        } else {
            // Handle the case when the payment with the given ID does not exist
            return null;
        }
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
