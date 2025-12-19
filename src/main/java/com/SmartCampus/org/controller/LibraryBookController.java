package com.SmartCampus.org.controller;

import com.SmartCampus.org.dto.LibraryBookDTO;
import com.SmartCampus.org.service.LibraryBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library/books")
public class LibraryBookController {

    private final LibraryBookService bookService;

    @Autowired
    public LibraryBookController(LibraryBookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<LibraryBookDTO> addBook(@Valid @RequestBody LibraryBookDTO dto) {
        return new ResponseEntity<>(bookService.addBook(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LibraryBookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}