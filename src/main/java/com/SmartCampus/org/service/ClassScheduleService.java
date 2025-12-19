package com.SmartCampus.org.service;

import com.SmartCampus.org.dto.ClassScheduleDTO;
import java.util.List;

public interface ClassScheduleService {
    ClassScheduleDTO createSchedule(ClassScheduleDTO dto);
    List<ClassScheduleDTO> getSchedulesByCourse(Long courseId);
    void deleteSchedule(Long id);
}