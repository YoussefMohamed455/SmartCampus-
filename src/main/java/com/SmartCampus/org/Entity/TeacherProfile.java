package com.SmartCampus.org.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherProfile {

    @Id
    private Long teacherId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "teacher_id")
    private User user;

    // These were missing and caused your error
    private String firstName;
    private String lastName;
    private String department;
    private String qualification;
    private String phone;

    // Relationships (Keep these since you have the entities)
    @OneToMany(mappedBy = "teacherProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> coursesTaught;

    @OneToMany(mappedBy = "teacherProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassSchedule> schedules;

    @OneToMany(mappedBy = "postedBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notice> noticesPosted;
}