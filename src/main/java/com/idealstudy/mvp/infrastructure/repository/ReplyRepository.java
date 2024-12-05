package com.idealstudy.mvp.infrastructure.repository;


import com.idealstudy.mvp.application.dto.ReplyDto;
import com.idealstudy.mvp.enums.classroom.Visibility;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReplyRepository {

    ReplyDto create(String content, Visibility visibility, Long parentCommentId, Long classInquiryId, 
        Long postId, String createdBy);  

    Page<ReplyDto> findList(Pageable pageable);

    ReplyDto findById(Long commentId);

    ReplyDto update(Long id, String content, Visibility visibility);

    void delete(Long commentId);
}
