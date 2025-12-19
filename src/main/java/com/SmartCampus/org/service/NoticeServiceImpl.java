package com.SmartCampus.org.service;

import com.SmartCampus.org.Entity.Notice;
import com.SmartCampus.org.Entity.TeacherProfile;
import com.SmartCampus.org.dto.NoticeDTO;
import com.SmartCampus.org.mapper.NoticeMapper;
import com.SmartCampus.org.repositories.NoticeRepository;
import com.SmartCampus.org.repositories.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final TeacherRepository teacherRepository;
    private final NoticeMapper noticeMapper;

    @Autowired
    public NoticeServiceImpl(NoticeRepository noticeRepository,
                             TeacherRepository teacherRepository,
                             NoticeMapper noticeMapper) {
        this.noticeRepository = noticeRepository;
        this.teacherRepository = teacherRepository;
        this.noticeMapper = noticeMapper;
    }

    @Override
    public NoticeDTO createNotice(NoticeDTO dto) {
        TeacherProfile teacher = teacherRepository.findById(dto.getPostedById())
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with id: " + dto.getPostedById()));

        Notice notice = noticeMapper.toEntity(dto);
        notice.setPostedBy(teacher);
        notice.setPostedDate(LocalDateTime.now()); // Set current time automatically

        Notice savedNotice = noticeRepository.save(notice);
        return noticeMapper.toDTO(savedNotice);
    }

    @Override
    public List<NoticeDTO> getAllNotices() {
        return noticeRepository.findAllByOrderByPostedDateDesc().stream()
                .map(noticeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NoticeDTO> getNoticesByTeacher(Long teacherId) {
        return noticeRepository.findByPostedBy_TeacherId(teacherId).stream()
                .map(noticeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteNotice(Long id) {
        if (!noticeRepository.existsById(id)) {
            throw new EntityNotFoundException("Notice not found");
        }
        noticeRepository.deleteById(id);
    }
}