package com.SmartCampus.org.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank; // Import this
import jakarta.validation.constraints.Size;     // Import this

@Data
public class StudentProfileDTO {
    private Long studentId;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Department is required")
    private String department;

    private String batch;

    @Size(min = 2, max = 20, message = "Roll number must be between 2 and 20 characters")
    private String rollNo;

    private Long userId;
}