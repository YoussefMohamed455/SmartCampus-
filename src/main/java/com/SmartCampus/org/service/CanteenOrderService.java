package com.SmartCampus.org.service;

import com.SmartCampus.org.dto.CanteenOrderDTO;
import java.util.List;

public interface CanteenOrderService {
    CanteenOrderDTO placeOrder(CanteenOrderDTO dto);
    List<CanteenOrderDTO> getOrdersByStudent(Long studentId);
}