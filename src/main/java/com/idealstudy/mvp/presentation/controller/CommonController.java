package com.idealstudy.mvp.presentation.controller;

import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.application.service.MemberService;
import com.idealstudy.mvp.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class CommonController {

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final MemberService memberService;

    @GetMapping("/")
    public ResponseEntity<String> main(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Claims claims = jwtUtil.getUserInfoFromToken(jwtUtil.getTokenFromRequest(request));
        String username = claims.getSubject();

        // 최초 로그인이면 사용자 상세 정보 수정 페이지로 리다이랙트.
        MemberDto dto = memberService.findByEmail(username);
        if(dto.isFirst()) {
            /*
             Sends a temporary redirect response to the client using the specified redirect location URL
             and clears the buffer.
             Calling this method sets the status code to SC_FOUND 302 (Found).
             This method can accept relative URLs.
             */
            response.sendRedirect("/api/users/"+dto.getUserId());
            return new ResponseEntity<>("최초 로그인입니다.", HttpStatusCode.valueOf(302));
        }

        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @GetMapping("/favicon.ico")
    public ResponseEntity<String> returnFavicon() {
        // 204 NO CONTENT
        return new ResponseEntity<>("현재 favicon은 존재하지 않음", HttpStatusCode.valueOf(204));
    }
}
