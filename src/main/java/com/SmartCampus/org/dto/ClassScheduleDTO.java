package com.SmartCampus.org.dto;

import com.SmartCampus.org.Entity.DayOfWeek;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
public class ClassScheduleDTO {

    private Long id;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    @NotNull(message = "Teacher ID is required")
    private Long teacherId;

    @NotNull(message = "Day of week is required")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    private String roomNumber;
}