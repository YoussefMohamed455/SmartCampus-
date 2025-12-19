package com.SmartCampus.org.mapper;

import com.SmartCampus.org.Entity.User;
import com.SmartCampus.org.dto.UserDTO;
import com.SmartCampus.org.Entity.Role;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Removed @Named logic. MapStruct will find the default methods below automatically.
    UserDTO toDTO(User user);

    @Mapping(target = "studentProfile", ignore = true)
    @Mapping(target = "teacherProfile", ignore = true)
    User toEntity(UserDTO userDTO);

    // Helper: MapStruct automatically uses this for Role -> String
    default String map(Role role) {
        return role != null ? role.name() : null;
    }

    // Helper: MapStruct automatically uses this for String -> Role
    default Role map(String role) {
        if (role == null) {
            return null;
        }
        try {
            return Role.valueOf(role);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}