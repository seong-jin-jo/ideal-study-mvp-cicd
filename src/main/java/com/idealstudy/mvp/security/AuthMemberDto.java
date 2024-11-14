package com.idealstudy.mvp.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Slf4j
public class AuthMemberDto extends User {

    private boolean fromSocial;

    public AuthMemberDto(String username, String password, boolean fromSocial,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.fromSocial = fromSocial;
    }

    public boolean isFromSocial() {
        return fromSocial;
    }
}
