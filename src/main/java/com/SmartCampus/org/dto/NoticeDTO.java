package com.SmartCampus.org.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class NoticeDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    private LocalDateTime postedDate;

    @NotNull(message = "Teacher ID (Poster) is required")
    private Long postedById;

    // Optional: We can add the teacher's name here for easier display on the frontend
    private String postedByName;
}