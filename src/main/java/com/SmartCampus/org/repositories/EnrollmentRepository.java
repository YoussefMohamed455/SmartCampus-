package com.SmartCampus.org.repositories;

import com.SmartCampus.org.Entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    // Allows fetching all enrollments for a specific student
    List<Enrollment> findByStudentProfile_StudentId(Long studentId);
}