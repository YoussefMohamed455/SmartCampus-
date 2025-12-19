package com.SmartCampus.org.service;

import com.SmartCampus.org.Entity.*;
import com.SmartCampus.org.dto.ExamDTO;
import com.SmartCampus.org.dto.ExamResultDTO;
import com.SmartCampus.org.mapper.ExamMapper;
import com.SmartCampus.org.repositories.CourseRepository;
import com.SmartCampus.org.repositories.ExamRepository;
import com.SmartCampus.org.repositories.ExamResultRepository;
import com.SmartCampus.org.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final ExamResultRepository resultRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final ExamMapper examMapper;

    @Autowired
    public ExamServiceImpl(ExamRepository examRepository, ExamResultRepository resultRepository,
                           CourseRepository courseRepository, StudentRepository studentRepository,
                           ExamMapper examMapper) {
        this.examRepository = examRepository;
        this.resultRepository = resultRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.examMapper = examMapper;
    }

    @Override
    public ExamDTO createExam(ExamDTO dto) {
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        Exam exam = examMapper.toEntity(dto);
        exam.setCourse(course);
        return examMapper.toDTO(examRepository.save(exam));
    }

    @Override
    public List<ExamDTO> getExamsByCourse(Long courseId) {
        return examRepository.findByCourse_Id(courseId).stream()
                .map(examMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExamResultDTO addResult(ExamResultDTO dto) {
        Exam exam = examRepository.findById(dto.getExamId())
                .orElseThrow(() -> new EntityNotFoundException("Exam not found"));

        StudentProfile student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        ExamResult result = examMapper.resultToEntity(dto);
        result.setExam(exam);
        result.setStudent(student);

        // Auto-calculate simple grade
        if (result.getGrade() == null) {
            double percentage = (result.getMarksObtained() / exam.getTotalMarks()) * 100;
            if (percentage >= 90) result.setGrade("A");
            else if (percentage >= 75) result.setGrade("B");
            else if (percentage >= 50) result.setGrade("C");
            else result.setGrade("F");
        }

        return examMapper.resultToDTO(resultRepository.save(result));
    }

    @Override
    public List<ExamResultDTO> getResultsByStudent(Long studentId) {
        return resultRepository.findByStudent_StudentId(studentId).stream()
                .map(examMapper::resultToDTO)
                .collect(Collectors.toList());
    }
}