package com.idealstudy.mvp.security.userDetailsImpl;

import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Getter
@RequiredArgsConstructor
@Slf4j
public class JwtDetails implements UserDetails {

    @Autowired
    private final String token;

    @Autowired
    private final JwtPayloadDto payload;

    // Returns the authorities granted to the user. Cannot return null.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        log.info("현재 유저의 권한: " + payload.getRole().toString());

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(payload.getRole().toString());
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return new Date().before(payload.getExp());
    }
}
