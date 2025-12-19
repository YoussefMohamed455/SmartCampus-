package com.SmartCampus.org.dto;

import com.SmartCampus.org.Entity.PaymentStatus;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class FeePaymentDTO {

    private Long id;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotNull(message = "Payment type is required")
    private String paymentType;

    private LocalDate paymentDate;
    private String transactionReference;
    private PaymentStatus status;
}