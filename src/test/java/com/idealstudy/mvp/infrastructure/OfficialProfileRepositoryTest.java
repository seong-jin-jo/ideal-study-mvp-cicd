package com.idealstudy.mvp.infrastructure;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.OfficialProfileEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OfficialProfileRepositoryTest {

    private final OfficialProfileRepository repository;

    @Autowired
    public OfficialProfileRepositoryTest(OfficialProfileRepository repository) {
        this.repository = repository;
    }

    @Test
    public void save() {

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

        OfficialProfileDto dto = OfficialProfileDto.builder()
                .content(html)
                .build();

        OfficialProfileEntity entity = OfficialProfileEntity.toEntity(dto);

        repository.create();

        String teacherId = "03da61c0-abc8-11ef-9ed4-0242ac1c0002";

        OfficialProfileDto resultDto = repository.findByTeacherId(teacherId);

        Assertions.assertThat(resultDto.getContent().isEmpty()).isFalse();
        Assertions.assertThat(resultDto.getContent()).contains(
                  "<section id=\"home\">"
                + "<h2>환영합니다</h2>"
                + "<p>이곳은 웹페이지의 메인 콘텐츠 영역입니다.</p>"
                + "</section>"
        );
    }
}
