package com.SmartCampus.org.controller;

import com.SmartCampus.org.dto.ClassScheduleDTO;
import com.SmartCampus.org.service.ClassScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ClassScheduleController {

    private final ClassScheduleService scheduleService;

    @Autowired
    public ClassScheduleController(ClassScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ClassScheduleDTO> createSchedule(@Valid @RequestBody ClassScheduleDTO dto) {
        return new ResponseEntity<>(scheduleService.createSchedule(dto), HttpStatus.CREATED);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ClassScheduleDTO>> getSchedulesByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(scheduleService.getSchedulesByCourse(courseId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}