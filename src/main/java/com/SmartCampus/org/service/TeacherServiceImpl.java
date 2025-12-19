package com.SmartCampus.org.service;

import com.SmartCampus.org.Entity.TeacherProfile;
import com.SmartCampus.org.Entity.User;
import com.SmartCampus.org.dto.TeacherProfileDTO;
import com.SmartCampus.org.mapper.TeacherMapper;
import com.SmartCampus.org.repositories.TeacherRepository;
import com.SmartCampus.org.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final TeacherMapper teacherMapper;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, UserRepository userRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public TeacherProfileDTO createTeacher(TeacherProfileDTO teacherDTO) {
        // 1. Fetch the User first
        User user = userRepository.findById(teacherDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + teacherDTO.getUserId()));

        // 2. Map DTO to Entity
        TeacherProfile teacher = teacherMapper.toEntity(teacherDTO);

        // 3. Link them
        teacher.setUser(user);

        TeacherProfile savedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toDTO(savedTeacher);
    }

    @Override
    public TeacherProfileDTO getTeacherById(Long id) {
        TeacherProfile teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with id: " + id));
        return teacherMapper.toDTO(teacher);
    }

    @Override
    public List<TeacherProfileDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(teacherMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherProfileDTO updateTeacher(Long id, TeacherProfileDTO teacherDTO) {
        TeacherProfile existingTeacher = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with id: " + id));

        existingTeacher.setFirstName(teacherDTO.getFirstName());
        existingTeacher.setLastName(teacherDTO.getLastName());
        existingTeacher.setDepartment(teacherDTO.getDepartment());
        existingTeacher.setQualification(teacherDTO.getQualification());
        existingTeacher.setPhone(teacherDTO.getPhone());

        TeacherProfile updatedTeacher = teacherRepository.save(existingTeacher);
        return teacherMapper.toDTO(updatedTeacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new EntityNotFoundException("Teacher not found with id: " + id);
        }
        teacherRepository.deleteById(id);
    }
}