package com.idealstudy.mvp.application.dto.classroom;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class FAQDto {

    private Long id;

    // classroomId 필요한 경우 추가해서 매핑 전략 추가 구상해야 함.

    private String title;

    private String content;

    ////////////////////////////////////////////////////////////////////////

    private LocalDateTime regDate; // 생성 일시

    private LocalDateTime modDate; // 수정 일시

    private LocalDateTime delDate; // 삭제 일시

    private String createdBy; // 생성자

    private String modifiedBy; // 수정자

    private String deletedBy; // 삭제자
}
