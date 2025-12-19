package com.SmartCampus.org.repositories;

import com.SmartCampus.org.Entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentProfile, Long> {
    // You can define custom query methods here if needed
    // Example: Optional<StudentProfile> findByEmail(String email);
}