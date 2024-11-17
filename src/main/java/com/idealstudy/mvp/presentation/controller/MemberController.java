package com.idealstudy.mvp.presentation.controller;

import com.idealstudy.mvp.application.MemberService;
import com.idealstudy.mvp.presentation.dto.SignUpUserRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    @Autowired
    private final MemberService memberService;

    // 어떤 JSON 값을 보내주면 좋을지 다시 생각해봐야 함.
    @PostMapping("/api/users/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpUserRequestDto dto) {

        // email 양식 검사 필요
        if(!isValidEmailPattern()){

        }

        try{
            log.info("입력받은 이메일: "+ dto.getEmail());
            // 동일한 이메일에 대해 중복으로 요청했으면 요청을 거부할 것.
            if(memberService.isEmailDuplication(dto.getEmail())) {
                return new ResponseEntity<String>("현재 등록 중이거나 이미 등록된 이메일입니다.",
                        HttpStatusCode.valueOf(400));
            }
            memberService.sendSignUpEmail(dto.getEmail());
        } catch (Exception e) {
            // 실패 메시지 반환
            log.error(e.toString() + ":: " + e.getMessage());
            return new ResponseEntity<String>("failed to send email", HttpStatusCode.valueOf(500));
        }

        // 성공 200 메시지 반환
        return new ResponseEntity<String>("success to send email", HttpStatusCode.valueOf(200));
    }

    @GetMapping("/api/users/email-authentication")
    public void emailAuthentication(@RequestParam String token) {
        log.info("사용자 토큰값: " + token);
    }

    @GetMapping("/api/users/{userId}")
    public void findMember(@PathVariable String userId) {

    }

    // page number는 어떻게 표현할 생각?
    @GetMapping("/api/users")
    public void findMemberList() {

    }

    @DeleteMapping("/api/users/{userId}")
    public void deleteMember(@PathVariable String userId) {

    }

    @PatchMapping("/api/users/{userId}")
    public void updateMember(@PathVariable String userId) {

    }


    private boolean isValidEmailPattern() {
        return true;
    }
}
