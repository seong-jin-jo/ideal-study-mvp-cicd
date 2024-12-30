package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.ClassroomPageResultDto;
import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-30T10:56:01+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class ClassroomMapperImpl implements ClassroomMapper {

    @Override
    public ClassroomResponseDto toDto(ClassroomEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ClassroomResponseDto classroomResponseDto = new ClassroomResponseDto();

        classroomResponseDto.setTitle( entity.getTitle() );
        classroomResponseDto.setDescription( entity.getDescription() );
        if ( entity.getCapacity() != null ) {
            classroomResponseDto.setCapacity( entity.getCapacity() );
        }
        classroomResponseDto.setCreatedBy( entity.getCreatedBy() );
        classroomResponseDto.setThumbnail( entity.getThumbnail() );

        return classroomResponseDto;
    }

    @Override
    public ClassroomEntity toEntity(ClassroomResponseDto dto) {
        if ( dto == null ) {
            return null;
        }

        ClassroomEntity.ClassroomEntityBuilder<?, ?> classroomEntity = ClassroomEntity.builder();

        classroomEntity.createdBy( dto.getCreatedBy() );
        classroomEntity.title( dto.getTitle() );
        classroomEntity.description( dto.getDescription() );
        classroomEntity.capacity( dto.getCapacity() );
        classroomEntity.thumbnail( dto.getThumbnail() );

        return classroomEntity.build();
    }

    @Override
    public ClassroomPageResultDto toPageResultDto(PageResultDto<ClassroomResponseDto, ClassroomEntity> pageResultDto) {
        if ( pageResultDto == null ) {
            return null;
        }

        ClassroomPageResultDto classroomPageResultDto = new ClassroomPageResultDto();

        List<ClassroomResponseDto> list = pageResultDto.getDtoList();
        if ( list != null ) {
            classroomPageResultDto.setDtoList( new ArrayList<ClassroomResponseDto>( list ) );
        }
        classroomPageResultDto.setTotalPage( pageResultDto.getTotalPage() );
        classroomPageResultDto.setPage( pageResultDto.getPage() );
        classroomPageResultDto.setSize( pageResultDto.getSize() );
        classroomPageResultDto.setStartPage( pageResultDto.getStartPage() );
        classroomPageResultDto.setEndPage( pageResultDto.getEndPage() );
        classroomPageResultDto.setHasPrev( pageResultDto.isHasPrev() );
        classroomPageResultDto.setHasNext( pageResultDto.isHasNext() );
        List<Integer> list1 = pageResultDto.getPageList();
        if ( list1 != null ) {
            classroomPageResultDto.setPageList( new ArrayList<Integer>( list1 ) );
        }

        return classroomPageResultDto;
    }
}
