package com.SmartCampus.org.repositories;

import com.SmartCampus.org.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // Optional: Find courses by code to prevent duplicates
    boolean existsByCourseCode(String courseCode);
}