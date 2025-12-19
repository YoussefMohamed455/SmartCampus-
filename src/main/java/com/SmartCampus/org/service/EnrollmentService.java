package com.SmartCampus.org.service;

import com.SmartCampus.org.dto.EnrollmentDTO;
import java.util.List;

public interface EnrollmentService {
    EnrollmentDTO enrollStudent(EnrollmentDTO enrollmentDTO);
    List<EnrollmentDTO> getEnrollmentsByStudent(Long studentId);
}