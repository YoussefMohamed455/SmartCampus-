package com.SmartCampus.org.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "student_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfile {

    @Id
    private Long studentId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id")
    private User user;

    private String firstName;
    private String lastName;
    private String department;
    private String batch;
    private String rollNo;

    @OneToMany(mappedBy = "studentProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "studentProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendances;

    // --- FIX IS HERE ---
    // Change "studentProfile" to "student" to match BookIssue.java
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<BookIssue> bookIssues;
}