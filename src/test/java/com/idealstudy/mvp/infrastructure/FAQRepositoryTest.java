package com.idealstudy.mvp.infrastructure;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.FAQDto;
import com.idealstudy.mvp.application.dto.classroom.FAQPageResultDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest
@Transactional
public class FAQRepositoryTest {

    private final FAQRepository faqRepository;

    private static final String TEACHER_ID = "98a10847-ad7e-11ef-8e5c-0242ac140002";

    private static final String CLASSROOM_ID = "98a12345-ad7e-11ef-8e5c-0242ac140002";

    private static final Long ID = 1000000L;

    @Autowired
    public FAQRepositoryTest(FAQRepository faqRepository) {
        this.faqRepository = faqRepository;
    }
    
    @Test
    public void createAndFindOne() {

        FAQDto resultDto = faqRepository.findById(ID);
        Assertions.assertThat(resultDto.getId()).isEqualTo(ID);
        Assertions.assertThat(resultDto.getCreatedBy()).isEqualTo(TEACHER_ID);
    }

    @Test
    public void findList() {

        PageRequestDto requestDto = PageRequestDto.builder()
                .page(1)
                .size(10)
                .build();

        FAQPageResultDto resultDto = faqRepository.findList(requestDto);
        List<FAQDto> list = resultDto.getDtoList();
        FAQDto dto = list.getFirst();

        Assertions.assertThat(resultDto.getPage()).isEqualTo(1);
        Assertions.assertThat(dto.getId()).isEqualTo(ID);
        Assertions.assertThat(dto.getCreatedBy()).isEqualTo(TEACHER_ID);
    }

    @Test
    public void update() {

        String newTitle = "동영상 시청 팁";
        FAQDto dto = FAQDto.builder()
                .id(ID)
                .title(newTitle)
                .build();

        FAQDto updateDto = faqRepository.update(dto);

        Assertions.assertThat(updateDto.getId()).isEqualTo(ID);
        Assertions.assertThat(updateDto.getCreatedBy()).isEqualTo(TEACHER_ID);
        Assertions.assertThat(updateDto.getTitle()).isEqualTo(newTitle);
    }

    @Test
    public void delete() {

        faqRepository.delete(ID);

        Assertions.assertThatThrownBy(() -> {faqRepository.findById(ID);})
                .isInstanceOf(NoSuchElementException.class);
    }
}
