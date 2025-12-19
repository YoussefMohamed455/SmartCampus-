package com.SmartCampus.org.service;

import com.SmartCampus.org.Entity.*;
import com.SmartCampus.org.dto.BookIssueDTO;
import com.SmartCampus.org.mapper.BookIssueMapper;
import com.SmartCampus.org.repositories.BookIssueRepository;
import com.SmartCampus.org.repositories.LibraryBookRepository;
import com.SmartCampus.org.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookIssueServiceImpl implements BookIssueService {

    private final BookIssueRepository issueRepository;
    private final LibraryBookRepository bookRepository;
    private final StudentRepository studentRepository;
    private final BookIssueMapper issueMapper;

    @Autowired
    public BookIssueServiceImpl(BookIssueRepository issueRepository,
                                LibraryBookRepository bookRepository,
                                StudentRepository studentRepository,
                                BookIssueMapper issueMapper) {
        this.issueRepository = issueRepository;
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
        this.issueMapper = issueMapper;
    }

    @Override
    @Transactional // Ensures stock update and issue happen together
    public BookIssueDTO issueBook(BookIssueDTO dto) {
        // 1. Fetch Book
        LibraryBook book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        // 2. Check Availability
        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("Book is currently unavailable");
        }

        // 3. Fetch Student
        StudentProfile student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        // 4. Create Issue Record
        BookIssue issue = new BookIssue();
        issue.setBook(book);
        issue.setStudent(student);
        issue.setIssueDate(LocalDate.now());
        issue.setExpectedReturnDate(LocalDate.now().plusWeeks(2)); // Default 2 weeks
        issue.setStatus(IssueStatus.ISSUED);

        // 5. Decrease Stock
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        BookIssue savedIssue = issueRepository.save(issue);
        return issueMapper.toDTO(savedIssue);
    }

    @Override
    @Transactional
    public BookIssueDTO returnBook(Long issueId) {
        BookIssue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new EntityNotFoundException("Issue record not found"));

        if (issue.getStatus() == IssueStatus.RETURNED) {
            throw new IllegalStateException("Book already returned");
        }

        // 1. Update Issue Record
        issue.setActualReturnDate(LocalDate.now());
        issue.setStatus(IssueStatus.RETURNED);

        // 2. Increase Stock
        LibraryBook book = issue.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        BookIssue savedIssue = issueRepository.save(issue);
        return issueMapper.toDTO(savedIssue);
    }

    @Override
    public List<BookIssueDTO> getActiveIssuesByStudent(Long studentId) {
        return issueRepository.findByStudent_StudentIdAndStatus(studentId, IssueStatus.ISSUED).stream()
                .map(issueMapper::toDTO)
                .collect(Collectors.toList());
    }
}