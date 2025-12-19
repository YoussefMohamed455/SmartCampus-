package com.SmartCampus.org.service;

import com.SmartCampus.org.dto.AttendanceDTO;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    AttendanceDTO markAttendance(AttendanceDTO dto);
    List<AttendanceDTO> getAttendanceByCourseAndDate(Long courseId, LocalDate date);
    List<AttendanceDTO> getAttendanceByStudent(Long studentId);
}