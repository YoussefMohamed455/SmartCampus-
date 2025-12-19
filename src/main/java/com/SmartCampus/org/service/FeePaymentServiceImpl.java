package com.SmartCampus.org.service;

import com.SmartCampus.org.Entity.FeePayment;
import com.SmartCampus.org.Entity.PaymentStatus;
import com.SmartCampus.org.Entity.StudentProfile;
import com.SmartCampus.org.dto.FeePaymentDTO;
import com.SmartCampus.org.mapper.FeePaymentMapper;
import com.SmartCampus.org.repositories.FeePaymentRepository;
import com.SmartCampus.org.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FeePaymentServiceImpl implements FeePaymentService {

    private final FeePaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final FeePaymentMapper paymentMapper;

    @Autowired
    public FeePaymentServiceImpl(FeePaymentRepository paymentRepository,
                                 StudentRepository studentRepository,
                                 FeePaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public FeePaymentDTO createPayment(FeePaymentDTO dto) {
        StudentProfile student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        FeePayment payment = paymentMapper.toEntity(dto);
        payment.setStudent(student);

        // Auto-set date if missing
        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDate.now());
        }

        // Generate a fake transaction ID for simulation
        if (payment.getTransactionReference() == null) {
            payment.setTransactionReference(UUID.randomUUID().toString());
        }

        // Default to PAID for now (in a real app, you'd integrate Stripe/PayPal here)
        if (payment.getStatus() == null) {
            payment.setStatus(PaymentStatus.PAID);
        }

        FeePayment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toDTO(savedPayment);
    }

    @Override
    public List<FeePaymentDTO> getPaymentsByStudent(Long studentId) {
        return paymentRepository.findByStudent_StudentId(studentId).stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FeePaymentDTO getPaymentById(Long id) {
        FeePayment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));
        return paymentMapper.toDTO(payment);
    }
}