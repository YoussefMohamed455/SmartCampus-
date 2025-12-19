package com.SmartCampus.org.mapper;

import com.SmartCampus.org.Entity.ClassSchedule;
import com.SmartCampus.org.dto.ClassScheduleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClassScheduleMapper {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "teacherProfile.teacherId", target = "teacherId")
    ClassScheduleDTO toDTO(ClassSchedule schedule);

    @Mapping(target = "course", ignore = true)
    @Mapping(target = "teacherProfile", ignore = true)
    ClassSchedule toEntity(ClassScheduleDTO scheduleDTO);
}