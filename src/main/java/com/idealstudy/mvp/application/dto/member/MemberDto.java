package com.idealstudy.mvp.application.dto.member;

import com.idealstudy.mvp.enums.member.*;
import lombok.*;

@Data
@Builder
public class MemberDto {

    // private Long id;
    private String userId;

    private String password;

    private String phoneAddress;

    private String email;

    private Gender sex;

    private String referralId;

    private Integer level;

    private Role role;
}
