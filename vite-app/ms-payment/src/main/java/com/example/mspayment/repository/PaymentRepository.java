// PaymentRepository.java
package com.example.mspayment.repository;

import com.example.mspayment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE p.checkDate BETWEEN :from AND :to")
    List<Payment> findPaymentsByDateRange(@Param("from") Date from, @Param("to") Date to);
    List<Payment> findPaymentsByStudentId(Long studentId);

    List<Payment> findPaymentsByCardName(String cardName);
}
