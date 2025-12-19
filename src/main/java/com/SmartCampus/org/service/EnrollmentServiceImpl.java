package com.SmartCampus.org.service;

import com.SmartCampus.org.Entity.Course;
import com.SmartCampus.org.Entity.Enrollment;
import com.SmartCampus.org.Entity.StudentProfile;
import com.SmartCampus.org.dto.EnrollmentDTO;
import com.SmartCampus.org.mapper.EnrollmentMapper;
import com.SmartCampus.org.repositories.CourseRepository;
import com.SmartCampus.org.repositories.EnrollmentRepository;
import com.SmartCampus.org.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentMapper enrollmentMapper;

    @Autowired
    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                 StudentRepository studentRepository,
                                 CourseRepository courseRepository,
                                 EnrollmentMapper enrollmentMapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentMapper = enrollmentMapper;
    }

    @Override
    public EnrollmentDTO enrollStudent(EnrollmentDTO enrollmentDTO) {
        // 1. Fetch Student
        StudentProfile student = studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + enrollmentDTO.getStudentId()));

        // 2. Fetch Course
        Course course = courseRepository.findById(enrollmentDTO.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + enrollmentDTO.getCourseId()));

        // 3. Create Enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentProfile(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDate.now());

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDTO(savedEnrollment);
    }

    @Override
    public List<EnrollmentDTO> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudentProfile_StudentId(studentId).stream()
                .map(enrollmentMapper::toDTO)
                .collect(Collectors.toList());
    }
}