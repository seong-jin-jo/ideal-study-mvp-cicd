package com.idealstudy.mvp.config;

import com.idealstudy.mvp.enums.member.Role;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {

        // The most basic example is to configure all URLs to require the role "ROLE_USER".
        // The configuration below requires authentication to every URL and will grant access
        // to both the user "admin" and "user".
        http.authorizeHttpRequests(auth ->
            auth
                    .requestMatchers("/*").permitAll()
                    // 정적 파일 허용
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    
                    .requestMatchers("/student/**").hasRole(Role.STUDENT.getRoleStr())
                    .requestMatchers("/teacher/**").hasRole(Role.TEACHER.getRoleStr())
                    .requestMatchers("/admin/**").hasRole(Role.ADMIN.getRoleStr())
        );

        // The most basic configuration defaults to automatically generating a login page at the URL "/login",
        // redirecting to "/login?error" for authentication failure.
        http.formLogin(formLogin ->
            formLogin
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginPage("/login")
                    .failureUrl("/login?failed")
                    .loginProcessingUrl("/login?process")
        );

        // Enables CSRF protection. This is activated by default when using EnableWebSecurity.
        // CSRF token 사용 시 POST 요청만 가능.
        http.csrf(csrf -> csrf.disable());

        // CORS 활성화
        http.cors(customizer -> customizer.configurationSource( // HttpServletRequest를 기반으로 CORS 설정을 반환
                new CorsConfigurationSource() { // CORS 설정을 직접 정의하는 익명 클래스
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) { // CORS 정책을 정의
                        CorsConfiguration config = new CorsConfiguration(); // corsConfiguration 객체를 생성하여 CORS 설정을 담을 컨테이너로 사용
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:3000")); // CORS 요청을 허용할 출처
                        config.setAllowedMethods(Collections.singletonList("*")); // CORS 요청을 허용할 메서드
                        config.setAllowCredentials(true); // CORS 쿠키나 인증정보를 포함한 요청 허용
                        config.setAllowedHeaders(Collections.singletonList("*")); // CORS 요청을 허용할 헤더
                        config.setMaxAge(3600L); // pre-flight 요청을 캐싱할 1시간
                        return config;
                    }
                }
        ));

        // This is automatically applied when using EnableWebSecurity.
        // The default is that accessing the URL "/logout" will log the user out by invalidating the HTTP Session,
        // cleaning up any rememberMe() authentication that was configured, clearing the SecurityContextHolder,
        // and then redirect to "/login?success".
        // The following customization to log out when the URL "/custom-logout" is invoked.
        // Log out will remove the cookie named "JSESSIONID", not invalidate the HttpSession,
        // clear the SecurityContextHolder, and upon completion redirect to "/".
        http.logout(logout -> logout.deleteCookies("JSESSIONID")
                .invalidateHttpSession(false)
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/login?success")
        );

        return http.build();
    }
}
