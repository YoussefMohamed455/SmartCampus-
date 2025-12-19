package com.SmartCampus.org.service;

import com.SmartCampus.org.dto.TeacherProfileDTO;
import java.util.List;

public interface TeacherService {
    TeacherProfileDTO createTeacher(TeacherProfileDTO teacherDTO);
    TeacherProfileDTO getTeacherById(Long id);
    List<TeacherProfileDTO> getAllTeachers();
    TeacherProfileDTO updateTeacher(Long id, TeacherProfileDTO teacherDTO);
    void deleteTeacher(Long id);
}