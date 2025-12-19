package com.SmartCampus.org.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class CourseDTO {

    private Long id;

    @NotBlank(message = "Course name is required")
    private String courseName;

    @NotBlank(message = "Course code is required")
    private String courseCode; // e.g., "CS101"

    @NotNull(message = "Credits are required")
    @Positive(message = "Credits must be positive")
    private Integer credits;
    @NotBlank(message = "Department is required")
    private String department;

    @NotNull(message = "Teacher ID is required")
    private Long teacherId; // We use ID to link, not the whole object
}