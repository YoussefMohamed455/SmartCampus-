package com.SmartCampus.org.repositories;

import com.SmartCampus.org.Entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // Get all notices posted by a specific teacher
    List<Notice> findByPostedBy_TeacherId(Long teacherId);

    // Get all notices ordered by date (newest first)
    List<Notice> findAllByOrderByPostedDateDesc();
}