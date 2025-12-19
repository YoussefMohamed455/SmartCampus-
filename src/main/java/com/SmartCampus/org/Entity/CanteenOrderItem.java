package com.SmartCampus.org.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "canteen_order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CanteenOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private CanteenOrder order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private CanteenItem item;

    private Integer quantity;
    private Double subTotal; // Price * Quantity
}