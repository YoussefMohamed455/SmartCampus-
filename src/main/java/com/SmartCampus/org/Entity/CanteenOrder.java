package com.SmartCampus.org.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "canteen_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CanteenOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentProfile student;

    private LocalDateTime orderDate;

    private Double totalPrice;

    // One order can have many items (e.g., 2 Burgers, 1 Coke)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    private List<CanteenOrderItem> items = new ArrayList<>();
}