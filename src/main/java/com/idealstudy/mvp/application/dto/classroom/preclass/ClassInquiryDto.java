package com.idealstudy.mvp.application.dto.classroom.preclass;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class ClassInquiryDto {

    private Long id;

    private String title;

    private String content;

    private String classroomId;

    private String createdBy;

    private LocalDateTime regDate;

    private LocalDateTime modDate;


}
