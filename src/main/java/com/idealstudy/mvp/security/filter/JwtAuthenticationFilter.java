package com.idealstudy.mvp.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.security.dto.UserLoginRequestDto;
import com.idealstudy.mvp.security.dto.UserLoginResponseDto;
import com.idealstudy.mvp.util.JwtUtil;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Slf4j(topic = "JwtAuthenticationFilter")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private final JwtUtil jwtUtil;

    @PostConstruct
    void setup() {
        // Sets the URL that determines if authentication is required
        setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        log.info("attempt authentication");

        try{
            log.info("request.getInputStream() = " + request.getInputStream());
            UserLoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
                    UserLoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    /*
                     This constructor can be safely used by any code that wishes to create
                     a UsernamePasswordAuthenticationToken, as the AbstractAuthenticationToken.isAuthenticated()
                     will return false.
                     */
                    new UsernamePasswordAuthenticationToken(
                            requestDto.username(),
                            requestDto.password()
                    )
            );
        } catch (IOException e) {
            log.error(e + " : " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        
        log.info("인증 성공. 인가 작업 수행");

        // Many of the authentication providers will create a UserDetails object as the principal.
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        String username = userDetails.getUsername();
        Role role = Role.fromString(
                userDetails.getAuthorities().stream().findFirst()
                        .map(GrantedAuthority::getAuthority)
                        .orElseThrow(() -> new IllegalStateException("No authority found"))
        );

        issueJwtToken(username, role, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        log.info("인증 실패.");

        // Sets the status code for this response.
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        throw new AccessDeniedException("인증에 실패하였습니다.");

        // Sends a temporary redirect response to the client using the specified redirect location URL
        // and clears the buffer.
        // response.sendRedirect("/auth/login");
    }

    private void issueJwtToken(String username, Role role, HttpServletResponse response) throws IOException {

        // 현재 sub 값이 email이 되는 구조임. userId를 넣고 싶으면 DB에서 값을 꺼내와야 하는데, Filter에서는 DB에 접근할 방법이 없음.
        String token = jwtUtil.createToken(username, role);
        jwtUtil.addJwtToCookie(token, response);

        UserLoginResponseDto responseDto = UserLoginResponseDto.create(username, token);
        // Sets the content type of the response being sent to the client, if the response has not been committed yet.
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        /*
         Sets the character encoding (MIME charset) of the response being sent to the client.
         If the character encoding has already been set by setContentType(java.lang.String)
         or setLocale(java.util.Locale), this method overrides it.
         */
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        ObjectMapper objectMapper = new ObjectMapper();

        // Writes a string.
        response.getWriter().write(objectMapper.writeValueAsString(responseDto));

        // Flushes the stream.
        response.getWriter().flush();
    }
}
