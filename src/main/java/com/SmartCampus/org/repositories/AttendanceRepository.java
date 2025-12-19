package com.SmartCampus.org.repositories;

import com.SmartCampus.org.Entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    // Find attendance for a specific course on a specific date
    List<Attendance> findByCourse_IdAndDate(Long courseId, LocalDate date);

    // Find all attendance records for a specific student
    List<Attendance> findByStudentProfile_StudentId(Long studentId);
}