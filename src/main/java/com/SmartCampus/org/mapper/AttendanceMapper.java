package com.SmartCampus.org.mapper;

import com.SmartCampus.org.Entity.Attendance;
import com.SmartCampus.org.dto.AttendanceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    @Mapping(source = "studentProfile.studentId", target = "studentId")
    @Mapping(source = "course.id", target = "courseId")
    AttendanceDTO toDTO(Attendance attendance);

    @Mapping(target = "studentProfile", ignore = true)
    @Mapping(target = "course", ignore = true)
    Attendance toEntity(AttendanceDTO attendanceDTO);
}