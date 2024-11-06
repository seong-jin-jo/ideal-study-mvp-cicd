package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.MemberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

    // Mapper class에서 이
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberDto entityToDto(MemberEntity entity);
    MemberEntity dtoToEntity(MemberDto dto);
}
