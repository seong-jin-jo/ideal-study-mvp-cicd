package com.idealstudy.mvp.application;

import com.idealstudy.mvp.application.service.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    private final MemberService memberService;

    @Autowired
    public MemberServiceTest(MemberService memberService) {
        this.memberService = memberService;
    }

    @Test
    public void testEncodePassword() {

        String raw = "1234";
        String encoded = "$2a$10$kdG9XoA8h0J7UirQ1xuUfuzVfa/BgGzZtEjmPc063.vrevHZfM6oK";

        boolean result = memberService.testPassword(raw, encoded);
        Assertions.assertThat(result).isTrue();
    }
}
