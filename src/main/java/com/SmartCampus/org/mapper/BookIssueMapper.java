package com.SmartCampus.org.mapper;

import com.SmartCampus.org.Entity.BookIssue;
import com.SmartCampus.org.dto.BookIssueDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookIssueMapper {

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "student.studentId", target = "studentId")
    BookIssueDTO toDTO(BookIssue bookIssue);

    @Mapping(target = "book", ignore = true)
    @Mapping(target = "student", ignore = true)
    BookIssue toEntity(BookIssueDTO bookIssueDTO);
}