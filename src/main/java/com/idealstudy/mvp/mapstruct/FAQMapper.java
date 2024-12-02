package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.FAQDto;
import com.idealstudy.mvp.application.dto.classroom.FAQPageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.FAQEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FAQMapper {

    FAQMapper INSTANCE = Mappers.getMapper(FAQMapper.class);


    FAQDto entityToDto(FAQEntity entity);
    FAQEntity dtoTOEntity(FAQDto dto);

    FAQPageResultDto toFAQPageResultDto(PageResultDto<FAQDto, FAQEntity> dto);
}
