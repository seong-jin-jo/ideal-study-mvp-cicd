package com.idealstudy.mvp.infrastructure;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.infrastructure.jpa.entity.OfficialProfileEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
public class OfficialProfileRepositoryTest {

    private final MemberRepository memberRepository;
    private final OfficialProfileRepository officialProfileRepository;

    private static final String USER_ID = UUID.randomUUID().toString();

    @Autowired
    public OfficialProfileRepositoryTest(MemberRepository memberRepository, OfficialProfileRepository repository) {
        this.memberRepository = memberRepository;
        this.officialProfileRepository = repository;
    }

    @Test

    public void save() {

        createDummyUser();

        officialProfileRepository.create(USER_ID);

        OfficialProfileDto dto = officialProfileRepository.findByTeacherId(USER_ID);
        Assertions.assertThat(dto).isNotNull();
        Assertions.assertThat(dto.getContent()).isEqualTo("<p>최초 프로필 생성됨</p>");
    }

    @Test
    public void update() {



        String html = "<body>\n" +
                "    <header>\n" +
                "        <h1>웹사이트 제목</h1>\n" +
                "        <nav>\n" +
                "            <ul>\n" +
                "                <li><a href=\"#home\">홈</a></li>\n" +
                "                <li><a href=\"#about\">소개</a></li>\n" +
                "                <li><a href=\"#contact\">연락처</a></li>\n" +
                "            </ul>\n" +
                "        </nav>\n" +
                "    </header>\n" +
                "\n" +
                "    <main>\n" +
                "        <section id=\"home\">\n" +
                "            <h2>환영합니다</h2>\n" +
                "            <p>이곳은 웹페이지의 메인 콘텐츠 영역입니다.</p>\n" +
                "        </section>\n" +
                "\n" +
                "        <section id=\"about\">\n" +
                "            <h2>소개</h2>\n" +
                "            <article>\n" +
                "                <p>여기에 회사나 개인에 대한 설명을 작성할 수 있습니다.</p>\n" +
                "            </article>\n" +
                "        </section>\n" +
                "\n" +
                "        <section id=\"contact\">\n" +
                "            <h2>연락처</h2>\n" +
                "            <form>\n" +
                "                <input type=\"text\" placeholder=\"이름\">\n" +
                "                <input type=\"email\" placeholder=\"이메일\">\n" +
                "                <textarea placeholder=\"메시지\"></textarea>\n" +
                "                <button type=\"submit\">보내기</button>\n" +
                "            </form>\n" +
                "        </section>\n" +
                "    </main>\n" +
                "\n" +
                "    <footer>\n" +
                "        <p>&copy; 2024 내 웹사이트. 모든 권리 reserved.</p>\n" +
                "    </footer>\n" +
                "</body>";

        OfficialProfileDto officialProfileDto = OfficialProfileDto.builder()
                .teacherId(USER_ID)
                .content(html)
                .build();

        OfficialProfileEntity entity = OfficialProfileEntity.toEntity(officialProfileDto);

        officialProfileRepository.create(USER_ID);

        String teacherId = "03da61c0-abc8-11ef-9ed4-0242ac1c0002";

        OfficialProfileDto resultDto = officialProfileRepository.findByTeacherId(teacherId);

        Assertions.assertThat(resultDto.getContent().isEmpty()).isFalse();
        Assertions.assertThat(resultDto.getContent()).contains(
                  "<section id=\"home\">"
                + "<h2>환영합니다</h2>"
                + "<p>이곳은 웹페이지의 메인 콘텐츠 영역입니다.</p>"
                + "</section>"
        );
    }

    @Deprecated
    private void createDummyUser() {

        /*
        MemberDto memberDto = MemberDto.builder()
                .userId(USER_ID)
                .password("abcd1234")
                .phoneAddress("010-1234-1234")
                .email("testteacher@gmail.com")
                .role(Role.ROLE_TEACHER)
                .sex(Gender.MALE)
                .referralId(UUID.randomUUID().toString())
                .fromSocial(0)
                .build();

        memberRepository.create(memberDto);
        */
    }
}
