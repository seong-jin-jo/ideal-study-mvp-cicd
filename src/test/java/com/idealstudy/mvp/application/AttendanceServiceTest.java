package com.idealstudy.mvp.application;

import com.idealstudy.mvp.TestRepositoryUtil;
import com.idealstudy.mvp.application.dto.classroom.inclass.AttendanceDto;
import com.idealstudy.mvp.application.service.classroom.inclass.AttendanceService;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import jakarta.servlet.http.HttpServletRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@SpringBootTest
@Transactional
public class AttendanceServiceTest {

    private final AttendanceService attendanceService;

    // 테스트 이외에 의존성 없음
    private final TestRepositoryUtil testRepositoryUtil;
    private Long autoIncrement;

    private static final String TEACHER_ID = "98a10847-ad7e-11ef-8e5c-0242ac140002";

    private static final String CLASSROOM_ID = "98a12345-ad7e-11ef-8e5c-0242ac140002";

    private static final String STUDENT_ID = "c99fd58f-b0ae-11ef-89d8-0242ac140003";

    private static final String OTHER_STUDENT_ID = "e8445639-917a-4396-8aaa-4a68dd11e4c7";

    private static final String PARENTS_ID = "c99fd83e-b0ae-11ef-89d8-0242ac140003";

    private static final Long EXIST_ID = 1L;

    private static final String TABLE_NAME = "attendance";

    // id 정보를 제공하기 위함
    private final HttpServletRequest request;

    @Autowired
    public AttendanceServiceTest(AttendanceService attendanceService, TestRepositoryUtil testRepositoryUtil, HttpServletRequest request) {
        this.attendanceService = attendanceService;
        this.testRepositoryUtil = testRepositoryUtil;
        this.request = request;
    }

    @BeforeEach
    public void getAutoIncrement() {
        autoIncrement = testRepositoryUtil.getAutoIncrement(TABLE_NAME);
    }

    @Test
    public void testCheckIn() {

        String classroomId = CLASSROOM_ID;
        String studentId = STUDENT_ID;
        setTokenId(studentId);

        AttendanceDto dto = attendanceService.checkIn(classroomId, studentId);
        Assertions.assertThat(dto.getCreatedBy()).isEqualTo(studentId);
        Assertions.assertThat(dto.getClassroomId()).isEqualTo(classroomId);
    }

    @Test
    public void testCheckOut() {

        String studentId = STUDENT_ID;

        createDummy(studentId);

        Long id = autoIncrement;
        LocalDateTime now = LocalDateTime.now();

        AttendanceDto dto = attendanceService.checkOut(id, studentId);
        Assertions.assertThat(dto.getCreatedBy()).isEqualTo(studentId);
        Assertions.assertThat(dto.getCheckOutDate()).isEqualTo(now);
    }

    @Test
    public void testGetIndividualAttendance() {

        String studentId = STUDENT_ID;
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        createDummy(studentId);

        List<AttendanceDto> list = attendanceService.getIndividualAttendance(studentId, now);
        Assertions.assertThat(list.size()).isEqualTo(1);
        Assertions.assertThat(list.getFirst().getRegDate().toLocalDate()).isEqualTo(now.toLocalDate());
        Assertions.assertThat(list.getFirst().getCreatedBy()).isEqualTo(studentId);
    }

    @Test
    public void testGetTodayClassroomAttendance() {

        String classroomId = CLASSROOM_ID;
        String teacherId = TEACHER_ID;
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));

        createDummy(STUDENT_ID);
        createDummy(OTHER_STUDENT_ID);

        List<AttendanceDto> list = attendanceService.getTodayClassroomAttendance(classroomId, now, teacherId);
        Assertions.assertThat(list.size()).isEqualTo(2);
        Assertions.assertThat(list.getFirst().getClassroomId()).isEqualTo(classroomId);
    }

    private void setTokenId(String userId) {

        JwtPayloadDto dto = JwtPayloadDto.builder()
                .sub(userId)
                .build();

        request.setAttribute("jwtPayload", dto);
    }

    private void createDummy(String userId) {

        setTokenId(userId);

        String classroomId = CLASSROOM_ID;

        attendanceService.checkIn(classroomId, userId);
    }
}
