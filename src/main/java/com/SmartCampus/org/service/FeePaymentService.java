package com.SmartCampus.org.service;

import com.SmartCampus.org.dto.FeePaymentDTO;
import java.util.List;

public interface FeePaymentService {
    FeePaymentDTO createPayment(FeePaymentDTO dto);
    List<FeePaymentDTO> getPaymentsByStudent(Long studentId);
    FeePaymentDTO getPaymentById(Long id);
}