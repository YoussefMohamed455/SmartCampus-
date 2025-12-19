package com.SmartCampus.org.controller;

import com.SmartCampus.org.dto.CanteenItemDTO;
import com.SmartCampus.org.service.CanteenItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/canteen/items")
public class CanteenItemController {

    private final CanteenItemService itemService;

    @Autowired
    public CanteenItemController(CanteenItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<CanteenItemDTO> addItem(@Valid @RequestBody CanteenItemDTO dto) {
        return new ResponseEntity<>(itemService.addItem(dto), HttpStatus.CREATED);
    }

    @GetMapping("/menu")
    public ResponseEntity<List<CanteenItemDTO>> getMenu() {
        return ResponseEntity.ok(itemService.getMenu());
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<CanteenItemDTO> updateAvailability(@PathVariable Long id, @RequestParam Boolean available) {
        return ResponseEntity.ok(itemService.updateAvailability(id, available));
    }
}