package com.idealstudy.mvp.application.dto.member;

import com.idealstudy.mvp.enums.member.*;
import lombok.*;

@Data
@Builder
public class MemberDto {

    private String userId;

    private String password;

    private String name;

    private String phoneAddress;

    private String email;

    private Gender sex;

    private String referralId;

    private Integer level;

    private Role role;

    private boolean fromSocial;
}
