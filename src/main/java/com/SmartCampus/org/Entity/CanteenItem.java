package com.SmartCampus.org.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "canteen_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CanteenItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // e.g., "Veg Burger"

    @Column(nullable = false)
    private Double price;

    private Boolean isAvailable; // To mark out-of-stock items
}