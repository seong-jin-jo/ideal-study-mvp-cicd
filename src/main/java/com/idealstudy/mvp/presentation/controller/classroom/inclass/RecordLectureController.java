package com.idealstudy.mvp.presentation.controller.classroom.inclass;

import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLectureDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLecturePageResultDto;
import com.idealstudy.mvp.application.service.classroom.ClassroomService;
import com.idealstudy.mvp.application.service.classroom.inclass.RecordLectureService;
import com.idealstudy.mvp.application.service.classroom.preclass.EnrollmentService;
import com.idealstudy.mvp.enums.error.SecurityErrorMsg;
import com.idealstudy.mvp.security.annotation.ForTeacher;
import com.idealstudy.mvp.security.annotation.ForUser;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import com.idealstudy.mvp.util.TryCatchControllerTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RecordLectureController {

    @Autowired
    private final RecordLectureService recordLectureService;

    @Autowired
    private final EnrollmentService enrollmentService;

    @Autowired
    private final ClassroomService classroomService;

    @ForTeacher
    @PostMapping("/api/recorded-lectures")
    public ResponseEntity<RecordLectureDto> create(@RequestBody RecordLectureDto dto, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");

        return TryCatchControllerTemplate.execute(() ->  {

            ClassroomResponseDto classroomDto = classroomService.getClassroomById(dto.getClassroomId());
            if( !classroomDto.getCreatedBy().equals(payload.getSub()))
                throw new SecurityException(SecurityErrorMsg.NOT_AFFILIATED.toString());

            return recordLectureService.create(
                    dto.getClassroomId(),
                    dto.getTitle(),
                    dto.getDescription(),
                    dto.getUrl(),
                    dto.getOrderNum()
            );
        });
    }

    @ForUser
    @GetMapping("/api/recorded-lectures/lecture/{lectureId}/{studentId}")
    public ResponseEntity<RecordLectureDto> getDetail(@PathVariable Long lectureId, @PathVariable String studentId) {

        return TryCatchControllerTemplate.execute(() -> {
            RecordLectureDto recordLectureDto = recordLectureService.getDetail(lectureId);

            boolean result = enrollmentService.belongToClassroom(recordLectureDto.getClassroomId(), studentId);

            if(result)
                return recordLectureDto;
            else
                throw new SecurityException(SecurityErrorMsg.NOT_AFFILIATED.toString());
        });
    }

    @GetMapping("/api/recorded-lectures/{classroomId}")
    public ResponseEntity<RecordLecturePageResultDto> selectList(@PathVariable String classroomId) {

        return TryCatchControllerTemplate.execute(() -> recordLectureService.selectList(classroomId));
    }

    @ForTeacher
    @PatchMapping("/api/recorded-lectures/{lectureId}")
    public ResponseEntity<RecordLectureDto> update(@PathVariable Long lectureId, @RequestBody RecordLectureDto dto,
                                                   HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");

        return TryCatchControllerTemplate.execute(() -> {

            ClassroomResponseDto classroomDto = classroomService.getClassroomById(dto.getClassroomId());
            if( !classroomDto.getCreatedBy().equals(payload.getSub()))
                throw new SecurityException(SecurityErrorMsg.NOT_AFFILIATED.toString());

            log.info("수정을 진행합니다.");
            return recordLectureService.update(
                    lectureId,
                    dto.getTitle(),
                    dto.getDescription(),
                    dto.getUrl(),
                    dto.getOrderNum()
            );
        });
    }

    @ForTeacher
    @DeleteMapping("/api/recorded-lectures/{lectureId}/{classroomId}")
    public ResponseEntity<Object> delete(@PathVariable Long lectureId, @PathVariable String classroomId,
                                         HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");

        return TryCatchControllerTemplate.execute(() -> {

            ClassroomResponseDto classroomDto = classroomService.getClassroomById(classroomId);
            if( !classroomDto.getCreatedBy().equals(payload.getSub()))
                throw new SecurityException(SecurityErrorMsg.NOT_AFFILIATED.toString());

            recordLectureService.delete(lectureId);
            return null;
        });
    }
}
