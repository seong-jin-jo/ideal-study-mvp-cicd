package com.idealstudy.mvp.security.service;

import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.enums.member.MemberError;
import com.idealstudy.mvp.infrastructure.MemberRepository;
import com.idealstudy.mvp.mapstruct.MemberMapper;
import com.idealstudy.mvp.security.AuthMemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("username: " + username);

        MemberDto result = null;
        try{
            result = memberRepository.findByEmail(username);
        } catch (NullPointerException e) {
            throw new UsernameNotFoundException(MemberError.NOT_REGISTERED_MEMBER.getMsg());
        }

        /*
        AuthMemberDto authMemberDto = new AuthMemberDto(
                result.getEmail(),
                result.getPassword(),
                result.isFromSocial(),
                result.getRole().strea
        );
        */


        return null;
    }
}
