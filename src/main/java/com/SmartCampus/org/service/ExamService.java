package com.SmartCampus.org.service;

import com.SmartCampus.org.dto.ExamDTO;
import com.SmartCampus.org.dto.ExamResultDTO;
import java.util.List;

public interface ExamService {
    ExamDTO createExam(ExamDTO dto);
    List<ExamDTO> getExamsByCourse(Long courseId);

    ExamResultDTO addResult(ExamResultDTO dto);
    List<ExamResultDTO> getResultsByStudent(Long studentId);
}