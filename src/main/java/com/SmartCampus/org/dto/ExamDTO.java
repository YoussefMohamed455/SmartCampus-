package com.SmartCampus.org.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ExamDTO {
    private Long id;

    @NotBlank(message = "Exam name is required")
    private String examName;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    private LocalDate examDate;

    @NotNull
    private Double totalMarks;
}