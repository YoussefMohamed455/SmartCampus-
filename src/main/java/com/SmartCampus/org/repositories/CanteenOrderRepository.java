package com.SmartCampus.org.repositories;

import com.SmartCampus.org.Entity.CanteenOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CanteenOrderRepository extends JpaRepository<CanteenOrder, Long> {
    List<CanteenOrder> findByStudent_StudentId(Long studentId);
}