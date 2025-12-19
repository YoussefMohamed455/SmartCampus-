package com.SmartCampus.org.service;

import com.SmartCampus.org.Entity.StudentProfile;
import com.SmartCampus.org.Entity.User; // Import User
import com.SmartCampus.org.dto.StudentProfileDTO;
import com.SmartCampus.org.mapper.StudentProfileMapper;
import com.SmartCampus.org.repositories.StudentRepository;
import com.SmartCampus.org.repositories.UserRepository; // Import UserRepo
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository; // Add this
    private final StudentProfileMapper studentProfileMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              UserRepository userRepository, // Inject this
                              StudentProfileMapper studentProfileMapper) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.studentProfileMapper = studentProfileMapper;
    }

    @Override
    public StudentProfileDTO createStudent(StudentProfileDTO studentDTO) {
        // 1. Fetch the existing User from the database
        User user = userRepository.findById(studentDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + studentDTO.getUserId()));

        // 2. Convert DTO to Entity
        StudentProfile student = studentProfileMapper.toEntity(studentDTO);

        // 3. CRITICAL: Link the User to the Student Profile
        // Because of @MapsId, the Student will take the ID of this User (e.g., ID 1)
        student.setUser(user);

        // 4. Save
        StudentProfile savedStudent = studentRepository.save(student);
        return studentProfileMapper.toDTO(savedStudent);
    }

    @Override
    public StudentProfileDTO getStudentById(Long id) {
        StudentProfile student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
        return studentProfileMapper.toDTO(student);
    }

    @Override
    public List<StudentProfileDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentProfileMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentProfileDTO updateStudent(Long id, StudentProfileDTO studentDTO) {
        StudentProfile existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));

        // Update fields
        existingStudent.setFirstName(studentDTO.getFirstName());
        existingStudent.setLastName(studentDTO.getLastName());
        existingStudent.setDepartment(studentDTO.getDepartment());
        existingStudent.setBatch(studentDTO.getBatch());
        existingStudent.setRollNo(studentDTO.getRollNo());

        StudentProfile updatedStudent = studentRepository.save(existingStudent);
        return studentProfileMapper.toDTO(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }
}