package com.idealstudy.mvp.security.filter;

import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import com.idealstudy.mvp.security.token.JwtAuthenticationToken;
import com.idealstudy.mvp.security.userDetailsImpl.JwtDetails;
import com.idealstudy.mvp.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j(topic = "JWT 토큰 필터(Not OAuth2.0)")
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        // request 보안 설정이 우선 적용되고, permitAll이 아닌 나머지에 대해서 filter에 걸리기를 기대함.
        super("/**");
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        log.info("JWT 토큰 검사 진행");
        try{
            String token = jwtUtil.getUserInfoFromToken(request);
            JwtPayloadDto payload = jwtUtil.getPayloadFromToken(token);
            JwtAuthenticationToken authToken = new JwtAuthenticationToken(null);
            authToken.setDetails(new JwtDetails(token, payload));
            return getAuthenticationManager().authenticate(authToken);
        } catch (Exception e) {
            log.error(e.toString() + " : " + e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        log.info("인증 성공!");

        JwtPayloadDto payload = jwtUtil.getPayloadFromToken(jwtUtil.getUserInfoFromToken(request));
        request.setAttribute("jwtPayload", payload);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
