package com.SmartCampus.org.controller;

import com.SmartCampus.org.dto.NoticeDTO;
import com.SmartCampus.org.service.NoticeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping
    public ResponseEntity<NoticeDTO> createNotice(@Valid @RequestBody NoticeDTO dto) {
        return new ResponseEntity<>(noticeService.createNotice(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NoticeDTO>> getAllNotices() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<NoticeDTO>> getNoticesByTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(noticeService.getNoticesByTeacher(teacherId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.noContent().build();
    }
}