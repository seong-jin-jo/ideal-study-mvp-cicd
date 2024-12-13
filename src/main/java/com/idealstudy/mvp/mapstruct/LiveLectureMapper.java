package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.LiveLectureDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.LiveLecturePageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.LiveLectureEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface LiveLectureMapper {

    LiveLectureMapper INSTANCE = Mappers.getMapper(LiveLectureMapper.class);

    LiveLectureDto toDto(LiveLectureEntity entity);

    LiveLectureEntity toEntity(LiveLectureDto dto);

    LiveLecturePageResultDto toPageResultDto(PageResultDto<LiveLectureDto, LiveLectureEntity> pageResultDto);
}
