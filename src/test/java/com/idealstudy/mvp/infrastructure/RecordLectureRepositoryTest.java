package com.idealstudy.mvp.infrastructure;

import com.idealstudy.mvp.TestRepositoryUtil;
import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLectureDto;
import com.idealstudy.mvp.infrastructure.repository.RecordLectureRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@SpringBootTest
@Transactional
public class RecordLectureRepositoryTest {

    private final RecordLectureRepository recordLectureRepository;

    // 테스트 이외에 의존성 없음

    @Autowired
    private final TestRepositoryUtil testRepositoryUtil;
    private Long autoIncrement;

    private static final String TEACHER_ID = "98a10847-ad7e-11ef-8e5c-0242ac140002";

    private static final String CLASSROOM_ID = "98a12345-ad7e-11ef-8e5c-0242ac140002";

    private static final String STUDENT_ID = "c99fd58f-b0ae-11ef-89d8-0242ac140003";

    private static final String PARENTS_ID = "c99fd83e-b0ae-11ef-89d8-0242ac140003";

    private static final Long EXIST_ID = 1L;

    private static final String TABLE_NAME = "record_lecture";

    private static final File TEST_FILE = new File("src/main/resources/static/video/test.mp4");


    @Autowired
    public RecordLectureRepositoryTest(RecordLectureRepository recordLectureRepository,
                                       TestRepositoryUtil testRepositoryUtil) {
        this.recordLectureRepository = recordLectureRepository;
        this.testRepositoryUtil = testRepositoryUtil;
    }

    @BeforeEach
    public void getAutoIncrement() {
        autoIncrement = testRepositoryUtil.getAutoIncrement(TABLE_NAME);
    }

    @Test
    public void testVimeoUpload(){

        String classroomId = CLASSROOM_ID;
        String title = "test";
        String description = "test";
        int order = 1;

        Assertions.assertThatCode(() ->
                recordLectureRepository.create(classroomId, title, description, TEST_FILE, order))
                .doesNotThrowAnyException();
    }

    @Test
    public void testVimeoEdit() {

        Long id = null;
        String videoEndPoint = "/videos/1037866255";
        String title = "update";
        String description = "update";

        Assertions.assertThatCode(() ->
                recordLectureRepository.updateVideo(id, title, description, videoEndPoint))
                .doesNotThrowAnyException();
    }

    @Test
    public void testVimeoDelete() {

        Long id = null;
        String videoEndPoint = "/videos/1037866255";

        Assertions.assertThatCode(() ->
                        recordLectureRepository.delete(id, videoEndPoint))
                .doesNotThrowAnyException();
    }

    @Test
    public void testCreate() {

        String classroomId = CLASSROOM_ID;
        String title = "test";
        String description = "test";
        int order = 1;

        RecordLectureDto dto = recordLectureRepository.create(classroomId, title, description, TEST_FILE, order);

        Assertions.assertThat(dto.getClassroomId()).isEqualTo(classroomId);
        Assertions.assertThat(dto.getTitle()).isEqualTo(title);
        Assertions.assertThat(dto.getDescription()).isEqualTo(description);
        Assertions.assertThat(dto.getOrderNum()).isEqualTo(order);
    }
}

