package com.SmartCampus.org.dto;

import com.SmartCampus.org.Entity.AttendanceStatus;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class AttendanceDTO {

    private Long id;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Status is required (PRESENT, ABSENT, LATE, EXCUSED)")
    private AttendanceStatus status;
}