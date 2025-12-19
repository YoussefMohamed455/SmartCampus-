package com.SmartCampus.org.controller;

import com.SmartCampus.org.dto.FeePaymentDTO;
import com.SmartCampus.org.service.FeePaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class FeePaymentController {

    private final FeePaymentService paymentService;

    @Autowired
    public FeePaymentController(FeePaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<FeePaymentDTO> createPayment(@Valid @RequestBody FeePaymentDTO dto) {
        return new ResponseEntity<>(paymentService.createPayment(dto), HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<FeePaymentDTO>> getStudentPayments(@PathVariable Long studentId) {
        return ResponseEntity.ok(paymentService.getPaymentsByStudent(studentId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeePaymentDTO> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }
}