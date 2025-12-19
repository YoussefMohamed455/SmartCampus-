package com.SmartCampus.org.mapper;

import com.SmartCampus.org.Entity.CanteenItem;
import com.SmartCampus.org.dto.CanteenItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CanteenItemMapper {
    CanteenItemDTO toDTO(CanteenItem item);
    CanteenItem toEntity(CanteenItemDTO dto);
}