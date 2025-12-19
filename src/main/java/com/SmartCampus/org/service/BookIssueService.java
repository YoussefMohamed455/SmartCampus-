package com.SmartCampus.org.service;

import com.SmartCampus.org.dto.BookIssueDTO;
import java.util.List;

public interface BookIssueService {
    BookIssueDTO issueBook(BookIssueDTO dto);
    BookIssueDTO returnBook(Long issueId);
    List<BookIssueDTO> getActiveIssuesByStudent(Long studentId);
}