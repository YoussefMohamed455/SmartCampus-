package com.SmartCampus.org.controller;

import com.SmartCampus.org.dto.ExamDTO;
import com.SmartCampus.org.dto.ExamResultDTO;
import com.SmartCampus.org.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamService examService;

    @Autowired
    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public ResponseEntity<ExamDTO> createExam(@Valid @RequestBody ExamDTO dto) {
        return new ResponseEntity<>(examService.createExam(dto), HttpStatus.CREATED);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ExamDTO>> getExamsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(examService.getExamsByCourse(courseId));
    }

    @PostMapping("/results")
    public ResponseEntity<ExamResultDTO> addResult(@Valid @RequestBody ExamResultDTO dto) {
        return new ResponseEntity<>(examService.addResult(dto), HttpStatus.CREATED);
    }

    @GetMapping("/results/student/{studentId}")
    public ResponseEntity<List<ExamResultDTO>> getStudentResults(@PathVariable Long studentId) {
        return ResponseEntity.ok(examService.getResultsByStudent(studentId));
    }
}