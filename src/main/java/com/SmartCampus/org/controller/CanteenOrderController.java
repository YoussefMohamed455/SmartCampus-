package com.SmartCampus.org.controller;

import com.SmartCampus.org.dto.CanteenOrderDTO;
import com.SmartCampus.org.service.CanteenOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/canteen/orders")
public class CanteenOrderController {

    private final CanteenOrderService orderService;

    @Autowired
    public CanteenOrderController(CanteenOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<CanteenOrderDTO> placeOrder(@Valid @RequestBody CanteenOrderDTO dto) {
        return new ResponseEntity<>(orderService.placeOrder(dto), HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<CanteenOrderDTO>> getStudentOrders(@PathVariable Long studentId) {
        return ResponseEntity.ok(orderService.getOrdersByStudent(studentId));
    }
}