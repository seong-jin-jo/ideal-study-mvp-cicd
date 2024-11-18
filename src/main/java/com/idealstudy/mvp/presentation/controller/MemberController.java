package com.idealstudy.mvp.presentation.controller;

import com.idealstudy.mvp.application.EmailService;
import com.idealstudy.mvp.application.MemberService;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.presentation.dto.SignUpUserRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @Autowired
    private final EmailService emailService;

    /*
     1. 로컬 파트(@ 앞 부분)는 영문자, 숫자, 그리고 일부 특수 문자(_+&*-)를 허용
     2. 도메인 파트(@ 뒷 부분)는 영문자, 숫자, 하이픈(-)을 허용
     3. 최상위 도메인(TLD)은 2~7자의 영문자로 제한
     */
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);

    // 어떤 JSON 값을 보내주면 좋을지 다시 생각해봐야 함.
    @PostMapping("/api/users/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpUserRequestDto dto) {

        ResponseEntity<String> response = sendEmail(dto.getEmail());
        if(response != null)
            return response;

        return new ResponseEntity<String>("success to send email", HttpStatusCode.valueOf(200));
    }

    @GetMapping("/api/users/email-authentication")
    public ResponseEntity<String> emailAuthentication(@RequestParam String token, @RequestParam String email) {
        log.info("사용자 email: " + email);
        log.info("사용자 토큰값: " + token);

        String password = null;
        try {
            // 현재 학생 회원가입만 가능
            password = memberService.addMember(email, token, Role.STUDENT);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatusCode.valueOf(400));
        }

        return new ResponseEntity<String>(password, HttpStatusCode.valueOf(200));
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

    private ResponseEntity<String> sendEmail(String email) {
        try{
            if(!isValidEmailPattern(email))
                return new ResponseEntity<String>("잘못된 이메일 양식입니다.", HttpStatusCode.valueOf(400));

            log.info("입력받은 이메일: "+ email);
            if(emailService.isEmailDuplication(email, memberService)) {
                return new ResponseEntity<String>("현재 등록 중이거나 이미 등록된 이메일입니다.",
                        HttpStatusCode.valueOf(400));
            }
            emailService.sendSignUpEmail(email);
        } catch (Exception e) {
            log.error(e.toString() + ":: " + e.getMessage());
            return new ResponseEntity<String>("failed to send email", HttpStatusCode.valueOf(500));
        }
        return null;
    }

    private boolean isValidEmailPattern(String email) {

        return emailPattern.matcher(email).matches();
    }
}
