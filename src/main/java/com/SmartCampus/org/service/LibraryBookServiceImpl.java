package com.SmartCampus.org.service;

import com.SmartCampus.org.Entity.LibraryBook;
import com.SmartCampus.org.dto.LibraryBookDTO;
import com.SmartCampus.org.mapper.LibraryBookMapper;
import com.SmartCampus.org.repositories.LibraryBookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryBookServiceImpl implements LibraryBookService {

    private final LibraryBookRepository bookRepository;
    private final LibraryBookMapper bookMapper;

    @Autowired
    public LibraryBookServiceImpl(LibraryBookRepository bookRepository, LibraryBookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public LibraryBookDTO addBook(LibraryBookDTO dto) {
        if (bookRepository.findByIsbn(dto.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("Book with this ISBN already exists");
        }

        LibraryBook book = bookMapper.toEntity(dto);
        // Initially, available copies = total copies
        book.setAvailableCopies(dto.getTotalCopies());

        LibraryBook savedBook = bookRepository.save(book);
        return bookMapper.toDTO(savedBook);
    }

    @Override
    public List<LibraryBookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LibraryBookDTO getBookById(Long id) {
        LibraryBook book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        return bookMapper.toDTO(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}