package com.idealstudy.mvp.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.security.dto.UserLoginRequestDto;
import com.idealstudy.mvp.security.dto.UserLoginResponseDto;
import com.idealstudy.mvp.util.JwtUtil;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j(topic = "JwtAuthenticationFilter")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

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
            log.error(e.getMessage());
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

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        log.info("인증 실패.");

        // Sets the status code for this response.
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        // Sends a temporary redirect response to the client using the specified redirect location URL
        // and clears the buffer.
        // response.sendRedirect("/auth/login");
    }
}
