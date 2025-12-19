package com.SmartCampus.org.service;

import com.SmartCampus.org.Entity.Attendance;
import com.SmartCampus.org.Entity.Course;
import com.SmartCampus.org.Entity.StudentProfile;
import com.SmartCampus.org.dto.AttendanceDTO;
import com.SmartCampus.org.mapper.AttendanceMapper;
import com.SmartCampus.org.repositories.AttendanceRepository;
import com.SmartCampus.org.repositories.CourseRepository;
import com.SmartCampus.org.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final AttendanceMapper attendanceMapper;

    @Autowired
    public AttendanceServiceImpl(AttendanceRepository attendanceRepository,
                                 StudentRepository studentRepository,
                                 CourseRepository courseRepository,
                                 AttendanceMapper attendanceMapper) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.attendanceMapper = attendanceMapper;
    }

    @Override
    public AttendanceDTO markAttendance(AttendanceDTO dto) {
        StudentProfile student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        Attendance attendance = attendanceMapper.toEntity(dto);
        attendance.setStudentProfile(student);
        attendance.setCourse(course);

        Attendance savedAttendance = attendanceRepository.save(attendance);
        return attendanceMapper.toDTO(savedAttendance);
    }

    @Override
    public List<AttendanceDTO> getAttendanceByCourseAndDate(Long courseId, LocalDate date) {
        return attendanceRepository.findByCourse_IdAndDate(courseId, date).stream()
                .map(attendanceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceDTO> getAttendanceByStudent(Long studentId) {
        return attendanceRepository.findByStudentProfile_StudentId(studentId).stream()
                .map(attendanceMapper::toDTO)
                .collect(Collectors.toList());
    }
}