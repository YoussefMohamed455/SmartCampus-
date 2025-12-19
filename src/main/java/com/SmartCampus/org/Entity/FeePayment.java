package com.SmartCampus.org.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "fee_payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentProfile student;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String paymentType; // e.g., "Tuition", "Library Fine", "Exam Fee"

    private LocalDate paymentDate;

    // Optional: Transaction ID (e.g., from Stripe/PayPal)
    private String transactionReference;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}