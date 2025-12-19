package com.SmartCampus.org.mapper;

import com.SmartCampus.org.Entity.StudentProfile;
import com.SmartCampus.org.dto.StudentProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudentProfileMapper {
    StudentProfileMapper INSTANCE = Mappers.getMapper(StudentProfileMapper.class);

    @Mapping(source = "user.id", target = "userId")
    StudentProfileDTO toDTO(StudentProfile student);

    @Mapping(source = "userId", target = "user.id")
    StudentProfile toEntity(StudentProfileDTO dto);
}
