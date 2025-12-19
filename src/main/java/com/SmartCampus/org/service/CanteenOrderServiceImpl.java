package com.SmartCampus.org.service;

import com.SmartCampus.org.Entity.*;
import com.SmartCampus.org.dto.CanteenOrderDTO;
import com.SmartCampus.org.repositories.CanteenItemRepository;
import com.SmartCampus.org.repositories.CanteenOrderRepository;
import com.SmartCampus.org.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CanteenOrderServiceImpl implements CanteenOrderService {

    private final CanteenOrderRepository orderRepository;
    private final StudentRepository studentRepository;
    private final CanteenItemRepository itemRepository;

    @Autowired
    public CanteenOrderServiceImpl(CanteenOrderRepository orderRepository,
                                   StudentRepository studentRepository,
                                   CanteenItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.studentRepository = studentRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    @Transactional
    public CanteenOrderDTO placeOrder(CanteenOrderDTO dto) {
        // 1. Fetch Student
        StudentProfile student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        // 2. Initialize Order
        CanteenOrder order = new CanteenOrder();
        order.setStudent(student);
        order.setOrderDate(LocalDateTime.now());
        order.setItems(new ArrayList<>()); // Initialize list

        double calculatedTotal = 0.0;

        // 3. Process Items
        for (CanteenOrderDTO.OrderItemRequest itemRequest : dto.getItems()) {
            CanteenItem item = itemRepository.findById(itemRequest.getItemId())
                    .orElseThrow(() -> new EntityNotFoundException("Item not found: " + itemRequest.getItemId()));

            CanteenOrderItem orderItem = new CanteenOrderItem();
            orderItem.setOrder(order); // Link to parent
            orderItem.setItem(item);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setSubTotal(item.getPrice() * itemRequest.getQuantity());

            order.getItems().add(orderItem);
            calculatedTotal += orderItem.getSubTotal();
        }

        order.setTotalPrice(calculatedTotal);

        // 4. Save (Cascade will save items too)
        CanteenOrder savedOrder = orderRepository.save(order);

        // 5. Convert to DTO manually (simpler here than Mapper due to nested list)
        return mapToDTO(savedOrder);
    }

    @Override
    public List<CanteenOrderDTO> getOrdersByStudent(Long studentId) {
        return orderRepository.findByStudent_StudentId(studentId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Helper method for mapping
    private CanteenOrderDTO mapToDTO(CanteenOrder order) {
        CanteenOrderDTO dto = new CanteenOrderDTO();
        dto.setId(order.getId());
        dto.setStudentId(order.getStudent().getStudentId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalPrice(order.getTotalPrice());

        List<CanteenOrderDTO.OrderItemRequest> itemDTOs = order.getItems().stream().map(i -> {
            CanteenOrderDTO.OrderItemRequest req = new CanteenOrderDTO.OrderItemRequest();
            req.setItemId(i.getItem().getId());
            req.setItemName(i.getItem().getName());
            req.setQuantity(i.getQuantity());
            req.setItemPrice(i.getSubTotal());
            return req;
        }).collect(Collectors.toList());

        dto.setItems(itemDTOs);
        return dto;
    }
}