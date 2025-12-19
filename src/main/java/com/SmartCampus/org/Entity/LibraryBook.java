package com.SmartCampus.org.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "library_books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibraryBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(unique = true, nullable = false)
    private String isbn;

    private Integer totalCopies;
    private Integer availableCopies;

    // --- FIX IS HERE ---
    // Changed "libraryBook" to "book" because that is the name of the variable in BookIssue.java
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookIssue> bookIssues;
}