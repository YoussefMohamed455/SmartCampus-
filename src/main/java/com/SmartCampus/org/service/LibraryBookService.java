package com.SmartCampus.org.service;

import com.SmartCampus.org.dto.LibraryBookDTO;
import java.util.List;

public interface LibraryBookService {
    LibraryBookDTO addBook(LibraryBookDTO dto);
    List<LibraryBookDTO> getAllBooks();
    LibraryBookDTO getBookById(Long id);
    void deleteBook(Long id);
}