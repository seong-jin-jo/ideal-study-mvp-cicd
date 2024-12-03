package com.idealstudy.mvp.presentation.controller.classroom;

import com.idealstudy.mvp.application.dto.classroom.FAQDto;
import com.idealstudy.mvp.application.dto.classroom.FAQPageResultDto;
import com.idealstudy.mvp.application.service.classroom.FAQService;
import com.idealstudy.mvp.presentation.dto.classroom.FAQRequestDto;
import com.idealstudy.mvp.security.annotation.ForTeacher;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FAQController {

    private final FAQService faqService;

    @ForTeacher
    @PostMapping("/api/faqs/{classroomId}")
    public ResponseEntity<String> create(HttpServletRequest request, @PathVariable String classroomId,
                                 @RequestBody FAQRequestDto requestDto) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String teacherId = payload.getSub();

        try {
            faqService.create(teacherId, classroomId, requestDto.getTitle(), requestDto.getContent());
        } catch (Exception e) {
            log.error(e + " : " + e.getMessage());
            return new ResponseEntity<String>("failed", HttpStatusCode.valueOf(400));
        }

        return new ResponseEntity<String>("success", HttpStatusCode.valueOf(200));
    }

    @GetMapping("/faqs/classes/{classId}")
    public ResponseEntity<FAQPageResultDto> searchList(@PathVariable String classId, @RequestParam Integer page) {

        FAQPageResultDto dto = faqService.findList(page, classId);

        return new ResponseEntity<FAQPageResultDto>(dto, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/faqs/{faqId}")
    public ResponseEntity<FAQDto> searchDetail(@PathVariable Long faqId) {

        FAQDto dto = faqService.findById(faqId);

        return new ResponseEntity<FAQDto>(dto, HttpStatusCode.valueOf(200));
    }

    @ForTeacher
    @PutMapping("/api/faqs/{faqId}")
    public ResponseEntity<?> update(@PathVariable Long faqId, @RequestBody FAQRequestDto dto,
                                         HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");

        // TEACHER 권한을 가진 다른 강사가 악의적으로 다른 클래스의 FAQ를 수정하는 것을 막아야 한다.
        FAQDto findDto = faqService.findById(faqId);
        if( !findDto.getCreatedBy().equals(payload.getSub())) {
            String errorMsg = "타인의 클래스에 등록된 FAQ 게시글을 임의로 수정할 수 없습니다.";
            log.error(errorMsg);
            return new ResponseEntity<String>(errorMsg, HttpStatusCode.valueOf(400));
        }

        FAQDto resultDto = faqService.update(faqId, dto.getTitle(), dto.getContent());

        return new ResponseEntity<FAQDto>(resultDto, HttpStatusCode.valueOf(200));
    }

    // 기존 FAQ의 CLASSID와 TEACHERID를 어딘가에서 비교해서, TEACHER 권한을 가진 사람이 악의적으로 다른 FAQ를 수정하는 것을 막아야 한다.
    @ForTeacher
    @DeleteMapping("/api/faqs/{faqId}")
    public ResponseEntity<String> delete(@PathVariable Long faqId, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");

        // TEACHER 권한을 가진 다른 강사가 악의적으로 다른 클래스의 FAQ를 수정하는 것을 막아야 한다.
        FAQDto findDto = faqService.findById(faqId);
        if( !findDto.getCreatedBy().equals(payload.getSub())) {
            String errorMsg = "타인의 클래스에 등록된 FAQ 게시글을 임의로 삭제할 수 없습니다.";
            log.error(errorMsg);
            return new ResponseEntity<String>(errorMsg, HttpStatusCode.valueOf(400));
        }

        if( !faqService.delete(faqId))
            return new ResponseEntity<String>("게시글 삭제에 실패했습니다.", HttpStatusCode.valueOf(500));

        return new ResponseEntity<String>("success", HttpStatusCode.valueOf(200));
    }
}
