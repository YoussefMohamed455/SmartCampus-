package com.SmartCampus.org.dto;

import com.SmartCampus.org.Entity.IssueStatus;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class BookIssueDTO {

    private Long id;

    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    private LocalDate issueDate;
    private LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;
    private IssueStatus status;
}