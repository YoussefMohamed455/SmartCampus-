package com.SmartCampus.org.service;

import com.SmartCampus.org.dto.CanteenItemDTO;
import java.util.List;

public interface CanteenItemService {
    CanteenItemDTO addItem(CanteenItemDTO dto);
    List<CanteenItemDTO> getMenu(); // Returns only available items
    List<CanteenItemDTO> getAllItems(); // Returns everything (for admin)
    CanteenItemDTO updateAvailability(Long id, Boolean isAvailable);
}