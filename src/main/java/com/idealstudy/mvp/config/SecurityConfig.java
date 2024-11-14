package com.idealstudy.mvp.config;

import com.idealstudy.mvp.enums.member.Role;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) {

        return null;
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth ->
            auth
                    // 정적 파일 허용
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
        );
        setGuestPermission(http);
        setUserPermission(http);
        setAdminPermission(http);
        setStudentPermission(http);
        setTeacherPermission(http);

        // Enables CSRF protection. This is activated by default when using EnableWebSecurity.
        // CSRF token 사용 시 POST 요청만 가능.
        http.csrf(AbstractHttpConfigurer::disable);

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

        // Configures HTTP Basic authentication.
        http.httpBasic(Customizer.withDefaults());

        // configuring of Session Management: stateless
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

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

    @Bean
    public UserDetailsService userDetailsService() {



        return null;
    }

    private void setGuestPermission(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/offcialProfile/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/sign-up").permitAll()
        );
    }

    private void setUserPermission(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/auth/logout").hasAnyRole(Role.ADMIN.getRole(),
                        Role.STUDENT.getRole(),
                        Role.TEACHER.getRole())
                .requestMatchers(HttpMethod.GET, "/api/users/*").hasAnyRole(Role.ADMIN.getRole(),
                        Role.STUDENT.getRole(),
                        Role.TEACHER.getRole())
                .requestMatchers(HttpMethod.PATCH, "/api/users/*").hasAnyRole(Role.ADMIN.getRole(),
                        Role.STUDENT.getRole(),
                        Role.TEACHER.getRole())
                .requestMatchers(HttpMethod.GET, "/api/mypage/**").hasAnyRole(Role.ADMIN.getRole(),
                        Role.STUDENT.getRole(),
                        Role.TEACHER.getRole())
                .requestMatchers(HttpMethod.GET, "/api/mypage/**").hasAnyRole(Role.ADMIN.getRole(),
                        Role.STUDENT.getRole(),
                        Role.TEACHER.getRole())
        );
    }

    private void setStudentPermission(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/student/**").hasRole(Role.STUDENT.getRole())

        );
    }

    private void setTeacherPermission(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/oficialProfile/*").hasRole(Role.TEACHER.getRole())
        );
    }

    private void setAdminPermission(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/users").hasRole(Role.ADMIN.getRole())
                .requestMatchers(HttpMethod.DELETE, "/api/users/*").hasRole(Role.ADMIN.getRole())
        );
    }
}
