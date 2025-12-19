package com.SmartCampus.org.mapper;

import com.SmartCampus.org.Entity.Enrollment;
import com.SmartCampus.org.dto.EnrollmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mapping(source = "studentProfile.studentId", target = "studentId")
    @Mapping(source = "course.id", target = "courseId")
    EnrollmentDTO toDTO(Enrollment enrollment);

    @Mapping(target = "studentProfile", ignore = true) // Handled in Service
    @Mapping(target = "course", ignore = true)         // Handled in Service
    Enrollment toEntity(EnrollmentDTO enrollmentDTO);
}