package com.SmartCampus.org.service;

import com.SmartCampus.org.Entity.Course;
import com.SmartCampus.org.Entity.TeacherProfile;
import com.SmartCampus.org.dto.CourseDTO;
import com.SmartCampus.org.mapper.CourseMapper;
import com.SmartCampus.org.repositories.CourseRepository;
import com.SmartCampus.org.repositories.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final CourseMapper courseMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, TeacherRepository teacherRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.courseMapper = courseMapper;
    }

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        // 1. Validate Teacher exists
        TeacherProfile teacher = teacherRepository.findById(courseDTO.getTeacherId())
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with id: " + courseDTO.getTeacherId()));

        // 2. Convert & Link
        Course course = courseMapper.toEntity(courseDTO);
        course.setTeacherProfile(teacher);

        Course savedCourse = courseRepository.save(course);
        return courseMapper.toDTO(savedCourse);
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
        return courseMapper.toDTO(course);
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        // 1. Fetch existing course FIRST
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

        // 2. NOW you can update the fields
        existingCourse.setCourseName(courseDTO.getCourseName());
        existingCourse.setCourseCode(courseDTO.getCourseCode());
        existingCourse.setCredits(courseDTO.getCredits());

        // This is the line that was in the wrong place
        existingCourse.setDepartment(courseDTO.getDepartment());

        // Update Teacher if changed
        if (!existingCourse.getTeacherProfile().getTeacherId().equals(courseDTO.getTeacherId())) {
            TeacherProfile newTeacher = teacherRepository.findById(courseDTO.getTeacherId())
                    .orElseThrow(() -> new EntityNotFoundException("Teacher not found with id: " + courseDTO.getTeacherId()));
            existingCourse.setTeacherProfile(newTeacher);
        }

        Course updatedCourse = courseRepository.save(existingCourse);
        return courseMapper.toDTO(updatedCourse);
    }
    @Override
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new EntityNotFoundException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }
}