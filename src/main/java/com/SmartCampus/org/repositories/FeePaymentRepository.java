package com.SmartCampus.org.repositories;

import com.SmartCampus.org.Entity.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {
    // Get all payments for a specific student
    List<FeePayment> findByStudent_StudentId(Long studentId);
}