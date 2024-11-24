package com.idealstudy.mvp.security.userDetailsImpl;

import com.idealstudy.mvp.application.dto.member.MemberDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@RequiredArgsConstructor
@Slf4j
public class MemberDetails implements UserDetails {

    @Autowired
    private final MemberDto memberDto;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        log.info("현재 유저의 권한: " + memberDto.getRole().toString());
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(memberDto.getRole().toString());

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return memberDto.getPassword();
    }

    @Override
    public String getUsername() {
        return memberDto.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
