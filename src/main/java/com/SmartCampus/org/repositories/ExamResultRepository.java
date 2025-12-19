package com.SmartCampus.org.repositories;

import com.SmartCampus.org.Entity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {
    List<ExamResult> findByStudent_StudentId(Long studentId);
    List<ExamResult> findByExam_Id(Long examId);
}