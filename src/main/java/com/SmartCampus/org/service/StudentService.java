package com.SmartCampus.org.service;

import com.SmartCampus.org.dto.StudentProfileDTO;
import java.util.List; // <--- You were missing this import!

public interface StudentService {

    StudentProfileDTO createStudent(StudentProfileDTO studentProfileDTO);

    StudentProfileDTO getStudentById(Long id);

    List<StudentProfileDTO> getAllStudents();

    StudentProfileDTO updateStudent(Long id, StudentProfileDTO studentProfileDTO);

    void deleteStudent(Long id);
}