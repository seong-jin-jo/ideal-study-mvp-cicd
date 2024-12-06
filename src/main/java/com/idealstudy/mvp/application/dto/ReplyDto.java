package com.idealstudy.mvp.application.dto;

import java.time.LocalDateTime;

import com.idealstudy.mvp.enums.classroom.Visibility;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ReplyDto {

    private Long commentId;

    private String content;

    private Visibility visibility;

    private String createdBy;

    private LocalDateTime modDate;

    private Long parentCommentId;
    
    private Long classInquiryId;

    private Long postId;

    // Like와 일대다 양방향 연관관계
}
