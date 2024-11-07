package com.idealstudy.mvp.infrastructure.impl;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.infrastructure.MemberRepository;
import com.idealstudy.mvp.infrastructure.jpa.entity.MemberEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.MemberJpaRepository;
import com.idealstudy.mvp.mapstruct.MemberMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Function;

@Repository
@Log4j2
public class MemberRepositoryImpl implements MemberRepository {

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private MemberMapper memberMapper;


    @Override
    public void create(MemberDto dto) {

        MemberEntity entity = memberMapper.dtoToEntity(dto);
        memberJpaRepository.save(entity);
    }

    @Override
    public MemberDto findById(Long id) {

        Optional<MemberEntity> result = memberJpaRepository.findById(id);

        return result.map(memberEntity -> memberMapper.entityToDto(memberEntity)).orElse(null);
    }

    @Override
    public PageResultDto<MemberDto, MemberEntity> findMembers(PageRequestDto requestDto) {

        Pageable pageable = requestDto.getPageable(Sort.by("id").ascending());

        Page<MemberEntity> result = memberJpaRepository.findAll(pageable);

        Function<MemberEntity, MemberDto> fn = (entity -> memberMapper.entityToDto(entity));

        return new PageResultDto<>(result, fn);
    }

    @Override
    public MemberDto update(MemberDto dto) {

        MemberEntity entity = memberJpaRepository.findById(dto.getId()).orElseThrow();
        memberMapper.updateEntityFromDto(dto, entity);

        log.info("update Entity: " + entity);

        MemberEntity result = memberJpaRepository.save(entity);

        return memberMapper.entityToDto(result);
    }

    @Override
    public void deleteById(Long id) {

        memberJpaRepository.deleteById(id);
    }
}
