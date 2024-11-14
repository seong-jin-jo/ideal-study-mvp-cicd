package com.idealstudy.mvp.infrastructure;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.MemberEntity;


public interface MemberRepository {

    void create(MemberDto dto);

    MemberDto findById(String id);

    MemberDto findByEmail(String email);

    PageResultDto<MemberDto, MemberEntity> findMembers(PageRequestDto requestDto);

    MemberDto update(MemberDto dto);

    void deleteById(String id);
}
