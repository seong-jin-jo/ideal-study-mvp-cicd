package com.idealstudy.mvp.infrastructure;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.member.*;


public interface MemberRepository {


    void create(TeacherDto dto);
    void create(ParentsDto dto);
    void create(StudentDto dto);
    // void create(AdminDto dto);

    MemberDto findById(String id);
    TeacherDto findTeacherById(String id);
    ParentsDto findParentsById(String id);
    StudentDto findStudentById(String id);
    // AdminDto findAdminById(String id);

    MemberDto findByEmail(String email);

    MemberPageResultDto findMembers(PageRequestDto requestDto);

    MemberDto update(MemberDto dto);
    TeacherDto update(TeacherDto dto);
    ParentsDto update(ParentsDto dto);
    StudentDto update(StudentDto dto);
    // AdminDto update(AdminDto dto);

    /**
     * 회원 탈퇴 시 DB에 완전히 제거하는 것이 아니라 상태값을 변경하는 것으로 처리한다.
     * @param id
     */
    boolean deleteById(String id);
}
