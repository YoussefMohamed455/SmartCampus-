package com.SmartCampus.org.mapper;

import com.SmartCampus.org.Entity.Exam;
import com.SmartCampus.org.Entity.ExamResult;
import com.SmartCampus.org.dto.ExamDTO;
import com.SmartCampus.org.dto.ExamResultDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExamMapper {

    @Mapping(source = "course.id", target = "courseId")
    ExamDTO toDTO(Exam exam);

    @Mapping(target = "course", ignore = true)
    Exam toEntity(ExamDTO dto);

    @Mapping(source = "exam.id", target = "examId")
    @Mapping(source = "student.studentId", target = "studentId")
    ExamResultDTO resultToDTO(ExamResult result);

    @Mapping(target = "exam", ignore = true)
    @Mapping(target = "student", ignore = true)
    ExamResult resultToEntity(ExamResultDTO dto);
}