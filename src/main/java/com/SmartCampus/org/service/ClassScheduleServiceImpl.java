package com.SmartCampus.org.service;

import com.SmartCampus.org.Entity.ClassSchedule;
import com.SmartCampus.org.Entity.Course;
import com.SmartCampus.org.Entity.TeacherProfile;
import com.SmartCampus.org.dto.ClassScheduleDTO;
import com.SmartCampus.org.mapper.ClassScheduleMapper;
import com.SmartCampus.org.repositories.ClassScheduleRepository;
import com.SmartCampus.org.repositories.CourseRepository;
import com.SmartCampus.org.repositories.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassScheduleServiceImpl implements ClassScheduleService {

    private final ClassScheduleRepository scheduleRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final ClassScheduleMapper scheduleMapper;

    @Autowired
    public ClassScheduleServiceImpl(ClassScheduleRepository scheduleRepository,
                                    CourseRepository courseRepository,
                                    TeacherRepository teacherRepository,
                                    ClassScheduleMapper scheduleMapper) {
        this.scheduleRepository = scheduleRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    public ClassScheduleDTO createSchedule(ClassScheduleDTO dto) {
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        TeacherProfile teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        ClassSchedule schedule = scheduleMapper.toEntity(dto);
        schedule.setCourse(course);
        schedule.setTeacherProfile(teacher);

        ClassSchedule savedSchedule = scheduleRepository.save(schedule);
        return scheduleMapper.toDTO(savedSchedule);
    }

    @Override
    public List<ClassScheduleDTO> getSchedulesByCourse(Long courseId) {
        return scheduleRepository.findByCourse_Id(courseId).stream()
                .map(scheduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
}