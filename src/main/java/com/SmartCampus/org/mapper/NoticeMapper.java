package com.SmartCampus.org.mapper;

import com.SmartCampus.org.Entity.Notice;
import com.SmartCampus.org.dto.NoticeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NoticeMapper {

    @Mapping(source = "postedBy.teacherId", target = "postedById")
    @Mapping(source = "postedBy.firstName", target = "postedByName") // Map name for display
    NoticeDTO toDTO(Notice notice);

    @Mapping(target = "postedBy", ignore = true)
    Notice toEntity(NoticeDTO noticeDTO);
}