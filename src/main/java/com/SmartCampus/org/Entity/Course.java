package com.SmartCampus.org.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Renamed from 'name' to match DTO/Service
    @Column(nullable = false)
    private String courseName;

    // Added this field (it was missing!)
    @Column(unique = true, nullable = false)
    private String courseCode;

    private Integer credits;

    // You can keep 'department' if you want, but ensure DTO has it if you want to pass it
    private String department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private TeacherProfile teacherProfile;

    // --- Relationships (Keep these!) ---
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassSchedule> classSchedules;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendances;
}