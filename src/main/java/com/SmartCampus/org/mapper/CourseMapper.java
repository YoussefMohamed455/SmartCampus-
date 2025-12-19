package com.SmartCampus.org.mapper;

import com.SmartCampus.org.Entity.Course;
import com.SmartCampus.org.dto.CourseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(source = "teacherProfile.teacherId", target = "teacherId")
    CourseDTO toDTO(Course course);

    @Mapping(target = "teacherProfile", ignore = true) // Handled in Service
    @Mapping(target = "enrollments", ignore = true)    // Ignore for now
    Course toEntity(CourseDTO courseDTO);
}