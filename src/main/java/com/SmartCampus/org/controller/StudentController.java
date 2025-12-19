package com.SmartCampus.org.controller;

import com.SmartCampus.org.dto.StudentProfileDTO;
import com.SmartCampus.org.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students") // This is the base URL for all endpoints below
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 1. Create a new student
    @PostMapping
    @Valid
    public ResponseEntity<StudentProfileDTO> createStudent(@Valid @RequestBody StudentProfileDTO studentDTO) {
        StudentProfileDTO createdStudent = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    // 2. Get a student by ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentProfileDTO> getStudentById(@PathVariable Long id) {
        StudentProfileDTO studentDTO = studentService.getStudentById(id);
        return ResponseEntity.ok(studentDTO);
    }

    // 3. Get all students
    @GetMapping
    public ResponseEntity<List<StudentProfileDTO>> getAllStudents() {
        List<StudentProfileDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // 4. Update a student
    @PutMapping("/{id}")
    @Valid
    public ResponseEntity<StudentProfileDTO> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentProfileDTO studentDTO) {
        StudentProfileDTO updatedStudent = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(updatedStudent);
    }

    // 5. Delete a student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}