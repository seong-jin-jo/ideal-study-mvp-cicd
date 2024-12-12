package com.idealstudy.mvp.presentation.controller.classroom;

import com.idealstudy.mvp.application.dto.classroom.ClassroomPageResultDto;
import com.idealstudy.mvp.presentation.dto.classroom.ClassroomRequestDto;
import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.application.service.classroom.ClassroomService;
import java.util.List;
import java.util.Objects;

import com.idealstudy.mvp.security.annotation.ForTeacher;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import com.idealstudy.mvp.util.TryCatchControllerTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ClassroomController {

    @Autowired
    private final ClassroomService service;

    @ForTeacher
    @PostMapping("/api/classes")
    public ResponseEntity<ClassroomResponseDto> createClass(@RequestBody ClassroomRequestDto request) {

        return TryCatchControllerTemplate.execute(() -> service.createClassroom(request));
    }

    @GetMapping("/api/classes")
    public ResponseEntity<ClassroomPageResultDto> getAllClasses() {

        return TryCatchControllerTemplate.execute(service::getAllClassrooms);
    }

    @GetMapping("/api/classes/{classId}")
    public ResponseEntity<ClassroomResponseDto> getClassById(@PathVariable String classId) {

        return TryCatchControllerTemplate.execute(() -> service.getClassroomById(classId));
    }

    @ForTeacher
    @PatchMapping("/api/classes/{classId}")
    public ResponseEntity<ClassroomResponseDto> updateClass(@PathVariable String classId,
                                                            @RequestBody ClassroomRequestDto requestDto,
                                                            HttpServletRequest request) {

        // client의 JSON 데이터를 신뢰할 수 없어서 token에서도 추출함.
        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String teacherId = payload.getSub();

        return TryCatchControllerTemplate.execute(() -> service.updateClassroom(classId, requestDto, teacherId));
    }

    @ForTeacher
    @DeleteMapping("/api/classes/{classId}")
    public ResponseEntity<Object> deleteClass(@PathVariable String classId, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String teacherId = payload.getSub();

        return TryCatchControllerTemplate.execute(() -> {
            service.deleteClassroom(classId, teacherId);
            return null;
        });
    }
}