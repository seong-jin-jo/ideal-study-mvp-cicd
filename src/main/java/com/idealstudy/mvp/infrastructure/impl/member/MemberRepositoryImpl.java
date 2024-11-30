package com.idealstudy.mvp.infrastructure.impl.member;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.member.*;
import com.idealstudy.mvp.enums.member.MemberError;
import com.idealstudy.mvp.infrastructure.MemberRepository;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.MemberEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.TeacherEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.member.MemberJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.member.TeacherJpaRepository;
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
    private TeacherJpaRepository teacherJpaRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public void create(TeacherDto dto) {

        dto.setFirst(true);

        TeacherEntity entity = memberMapper.dtoToEntity(dto);
        teacherJpaRepository.save(entity);
    }

    @Override
    public void create(ParentsDto dto) {

    }

    @Override
    public void create(StudentDto dto) {

    }

    @Override
    public void create(AdminDto dto) {

    }

    @Override
    public MemberDto findById(String id) {  // 조회 인자에는 반드시 ROle 값이 포함되어야 하며(instanceof로 다운 캐스팅 객체 판별), 반환값은 업캐스팅된 DTO 객체를 반환해야 함.

        Optional<MemberEntity> result = memberJpaRepository.findById(id);

        if(result.isEmpty())
            throw new NullPointerException(MemberError.NOT_REGISTERED_MEMBER.getMsg());

        // return memberMapper.entityToDto(result.get());
        return null;
    }

    @Override
    public MemberDto findByEmail(String email) {

        MemberEntity result = memberJpaRepository.findByEmail(email);

        if(result == null)
            throw new NullPointerException(MemberError.NOT_REGISTERED_MEMBER.getMsg());

        // return memberMapper.entityToDto(result);
        return null;
    }

    @Override
    public MemberPageResultDto findMembers(PageRequestDto requestDto) {

        Pageable pageable = requestDto.getPageable(Sort.by("regDate").ascending());

        Page<MemberEntity> result = memberJpaRepository.findAll(pageable);

        // Function<MemberEntity, MemberDto> fn = (entity -> memberMapper.entityToDto(entity));
        Function<MemberEntity, MemberDto> fn = null;

        return memberMapper.toApplicationPageResult(new PageResultDto<>(result, fn));
    }

    @Override
    public TeacherDto update(TeacherDto dto) {
        return null;
    }

    @Override
    public ParentsDto update(ParentsDto dto) {
        return null;
    }

    @Override
    public StudentDto update(StudentDto dto) {
        return null;
    }

    @Override
    public AdminDto update(AdminDto dto) {
        return null;
    }

    // @Override
    @Deprecated
    public MemberDto update(MemberDto dto) {

        MemberEntity entity = memberJpaRepository.findById(dto.getUserId()).orElseThrow();
        memberMapper.updateEntityFromDto(dto, entity);

        MemberEntity result = memberJpaRepository.save(entity);

        // return memberMapper.entityToDto(result);
        return null;
    }

    @Override
    public boolean deleteById(String id) {

        MemberDto dto = null;
        try {
            // dto = memberMapper.entityToDto(memberJpaRepository.findById(id).orElseThrow());
            dto.setDeleted(true);
            // memberJpaRepository.save(memberMapper.dtoToEntity(dto));
        } catch (Exception e) {
            log.info(e + " : " + e.getMessage());
            return false;
        }

        return true;
    }

}
