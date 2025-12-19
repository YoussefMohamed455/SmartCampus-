package com.SmartCampus.org.controller;

import com.SmartCampus.org.dto.BookIssueDTO;
import com.SmartCampus.org.service.BookIssueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library/issues")
public class BookIssueController {

    private final BookIssueService issueService;

    @Autowired
    public BookIssueController(BookIssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping("/borrow")
    public ResponseEntity<BookIssueDTO> borrowBook(@Valid @RequestBody BookIssueDTO dto) {
        return new ResponseEntity<>(issueService.issueBook(dto), HttpStatus.CREATED);
    }

    @PostMapping("/return/{issueId}")
    public ResponseEntity<BookIssueDTO> returnBook(@PathVariable Long issueId) {
        return ResponseEntity.ok(issueService.returnBook(issueId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<BookIssueDTO>> getStudentActiveIssues(@PathVariable Long studentId) {
        return ResponseEntity.ok(issueService.getActiveIssuesByStudent(studentId));
    }
}