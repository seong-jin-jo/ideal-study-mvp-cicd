package com.idealstudy.mvp.infrastructure;

import com.idealstudy.mvp.application.dto.classroom.ClassInquiryDto;
import com.idealstudy.mvp.application.dto.classroom.ClassInquiryPageResultDto;

public interface ClassInquiryRepository {

    ClassInquiryDto create(String title, String content, String classroomId, String writer);

    ClassInquiryPageResultDto findList(String classId, int page);

    ClassInquiryDto findById(Long inquiryId);

    ClassInquiryDto update(Long id, String title, String content, String classroomId, String writer);

    boolean delete(Long inquiryId);
}
