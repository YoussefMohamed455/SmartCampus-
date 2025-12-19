package com.SmartCampus.org.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class ExamResultDTO {
    private Long id;

    @NotNull(message = "Exam ID is required")
    private Long examId;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Marks obtained is required")
    private Double marksObtained;

    private String grade; // Optional: can be calculated in service
}