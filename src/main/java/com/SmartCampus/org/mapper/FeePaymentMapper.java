package com.SmartCampus.org.mapper;

import com.SmartCampus.org.Entity.FeePayment;
import com.SmartCampus.org.dto.FeePaymentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeePaymentMapper {

    @Mapping(source = "student.studentId", target = "studentId")
    FeePaymentDTO toDTO(FeePayment feePayment);

    @Mapping(target = "student", ignore = true)
    FeePayment toEntity(FeePaymentDTO feePaymentDTO);
}