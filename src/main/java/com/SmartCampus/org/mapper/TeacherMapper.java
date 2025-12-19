package com.SmartCampus.org.mapper;

import com.SmartCampus.org.Entity.TeacherProfile;
import com.SmartCampus.org.dto.TeacherProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherProfileDTO toDTO(TeacherProfile teacherProfile);

    @Mapping(target = "user", ignore = true) // We handle the User link in the Service manually
    TeacherProfile toEntity(TeacherProfileDTO teacherProfileDTO);
}