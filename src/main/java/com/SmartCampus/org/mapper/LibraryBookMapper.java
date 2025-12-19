package com.SmartCampus.org.mapper;

import com.SmartCampus.org.Entity.LibraryBook;
import com.SmartCampus.org.dto.LibraryBookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LibraryBookMapper {

    LibraryBookDTO toDTO(LibraryBook libraryBook);

    @Mapping(target = "availableCopies", ignore = true) // Set manually in service
    LibraryBook toEntity(LibraryBookDTO libraryBookDTO);
}