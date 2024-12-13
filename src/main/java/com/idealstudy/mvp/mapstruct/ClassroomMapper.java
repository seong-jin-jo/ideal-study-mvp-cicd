package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.ClassroomPageResultDto;
import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ClassroomMapper {

    ClassroomMapper INSTANCE = Mappers.getMapper(ClassroomMapper.class);

    ClassroomResponseDto toDto(ClassroomEntity entity);

    ClassroomEntity toEntity(ClassroomResponseDto dto);

    ClassroomPageResultDto toPageResultDto(PageResultDto<ClassroomResponseDto, ClassroomEntity> pageResultDto);
}
