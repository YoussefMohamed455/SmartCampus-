package com.SmartCampus.org.service;

import com.SmartCampus.org.dto.NoticeDTO;
import java.util.List;

public interface NoticeService {
    NoticeDTO createNotice(NoticeDTO dto);
    List<NoticeDTO> getAllNotices();
    List<NoticeDTO> getNoticesByTeacher(Long teacherId);
    void deleteNotice(Long id);
}