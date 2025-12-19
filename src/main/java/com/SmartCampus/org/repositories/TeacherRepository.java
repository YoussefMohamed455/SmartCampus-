package com.SmartCampus.org.repositories;

import com.SmartCampus.org.Entity.TeacherProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherProfile, Long> {
    // You can add custom queries here later if needed
}