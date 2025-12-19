package com.SmartCampus.org.dto;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CanteenOrderDTO {

    private Long id;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    private LocalDateTime orderDate;
    private Double totalPrice;

    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemRequest> items;

    // Inner class to handle the list of items in the JSON request
    @Data
    public static class OrderItemRequest {
        @NotNull
        private Long itemId;

        @NotNull
        private Integer quantity;

        // Optional: Used for response display
        private String itemName;
        private Double itemPrice;
    }
}