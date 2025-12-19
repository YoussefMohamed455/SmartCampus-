package com.SmartCampus.org.repositories;

import com.SmartCampus.org.Entity.BookIssue;
import com.SmartCampus.org.Entity.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookIssueRepository extends JpaRepository<BookIssue, Long> {
    // Find active issues for a student
    List<BookIssue> findByStudent_StudentIdAndStatus(Long studentId, IssueStatus status);
}