package com.idealstudy.mvp.application;

import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.infrastructure.MemberRepository;
import com.idealstudy.mvp.infrastructure.RedisRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final RedisRepository redisRepository;

    public String addMember(String email, String token, Role role) throws IllegalArgumentException {
        String savedToken = redisRepository.getToken(email);
        if( savedToken == null || !savedToken.equals(token))
            throw new IllegalArgumentException("유효한 토큰이 아님");

        String password = UUID.randomUUID().toString();
        memberRepository.create(MemberDto.builder()
                .userId(email)
                .password(password)
                .email(email)
                .fromSocial(false)
                .role(role)
                .build());

        return password;
    }

    public MemberDto findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
