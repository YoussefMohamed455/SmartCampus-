package com.SmartCampus.org.repositories;

import com.SmartCampus.org.Entity.CanteenItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CanteenItemRepository extends JpaRepository<CanteenItem, Long> {
    // Find only available items for the menu
    List<CanteenItem> findByIsAvailableTrue();
}