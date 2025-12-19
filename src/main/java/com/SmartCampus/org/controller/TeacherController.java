package com.SmartCampus.org.controller;

import com.SmartCampus.org.dto.TeacherProfileDTO;
import com.SmartCampus.org.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // 1. Create Teacher
    @PostMapping
    public ResponseEntity<TeacherProfileDTO> createTeacher(@Valid @RequestBody TeacherProfileDTO teacherDTO) {
        TeacherProfileDTO createdTeacher = teacherService.createTeacher(teacherDTO);
        return new ResponseEntity<>(createdTeacher, HttpStatus.CREATED);
    }

    // 2. Get Teacher by ID
    @GetMapping("/{id}")
    public ResponseEntity<TeacherProfileDTO> getTeacherById(@PathVariable Long id) {
        TeacherProfileDTO teacherDTO = teacherService.getTeacherById(id);
        return ResponseEntity.ok(teacherDTO);
    }

    // 3. Get All Teachers
    @GetMapping
    public ResponseEntity<List<TeacherProfileDTO>> getAllTeachers() {
        List<TeacherProfileDTO> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    // 4. Update Teacher
    @PutMapping("/{id}")
    public ResponseEntity<TeacherProfileDTO> updateTeacher(@PathVariable Long id, @Valid @RequestBody TeacherProfileDTO teacherDTO) {
        TeacherProfileDTO updatedTeacher = teacherService.updateTeacher(id, teacherDTO);
        return ResponseEntity.ok(updatedTeacher);
    }

    // 5. Delete Teacher
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}