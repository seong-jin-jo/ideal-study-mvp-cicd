package com.idealstudy.mvp.application;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.application.dto.member.TeacherDto;
import com.idealstudy.mvp.application.service.OfficialProfileService;
import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.enums.member.SchoolRegister;
import com.idealstudy.mvp.infrastructure.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
public class OfficialProfileServiceTest {

    private final OfficialProfileService officialProfileService;

    // 더미 데이터 생성용. 이외에 의존성 없음.
    private final MemberRepository memberRepository;

    private static final String TEACHER_ID = UUID.randomUUID().toString();

    @Autowired
    public OfficialProfileServiceTest(OfficialProfileService officialProfileService, MemberRepository memberRepository) {
        this.officialProfileService = officialProfileService;
        this.memberRepository = memberRepository;
    }

    @BeforeEach
    public void createDummyTeacher() {
        String univ = "한국대학교";
        SchoolRegister schoolRegister = SchoolRegister.GRADUATION;
        String subject = "수학";

        TeacherDto dto = TeacherDto.builder()
                .userId(TEACHER_ID)
                .password("abcd1234")
                .phoneAddress("010-1234-1234")
                .email("testteacher@gmail.com")
                .role(Role.ROLE_TEACHER)
                .sex(Gender.MALE)
                .referralId(UUID.randomUUID().toString())
                .fromSocial(0)
                .univ(univ)
                .status(schoolRegister)
                .subject(subject)
                .build();

        memberRepository.create(dto);
    }

    @Test
    public void saveAndFindOne() {

        officialProfileService.create(TEACHER_ID);

        OfficialProfileDto dto = officialProfileService.selectOne(TEACHER_ID);

        Assertions.assertThat(dto.getTeacherId()).isEqualTo(TEACHER_ID);
    }

    @Test
    public void update() {

        officialProfileService.create(TEACHER_ID);

        String html = "<h1>아무개 공식 프로필</h1>\n" +
                "<p>저는 수학 정말 잘합니다. 믿어주세요.</p>";

        OfficialProfileDto dto = OfficialProfileDto.builder()
                .teacherId(TEACHER_ID)
                .content(html)
                .build();

        OfficialProfileDto resultDto = officialProfileService.update(dto);

        Assertions.assertThat(resultDto).isNotNull();
        Assertions.assertThat(resultDto.getTeacherId()).isEqualTo(TEACHER_ID);
        Assertions.assertThat(resultDto.getContent()).isEqualTo(html);
    }
}
