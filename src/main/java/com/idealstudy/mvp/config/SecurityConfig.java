package com.idealstudy.mvp.config;

import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.error.ExceptionHandlerFilter;
import com.idealstudy.mvp.security.filter.JwtAuthenticationFilter;
import com.idealstudy.mvp.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@Log4j2
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private final JwtUtil jwtUtil;
    @Autowired
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        List<AuthenticationProvider> providers = new ArrayList<>();

        // UsernamePasswordAuthenticationToken 처리기
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        providers.add(daoAuthenticationProvider);

        return new ProviderManager(providers);
    }

    @Bean
    public ExceptionHandlerFilter exceptionHandlerFilter() {
        return new ExceptionHandlerFilter();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(userDetailsService, passwordEncoder()));
        return filter;
    }

    // OAuth2LoginAuthenticationFilter(현재 라이브러리 버전 미지원) 생성 및 설정 필요


    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {

        // Configures HTTP Basic authentication.
        http.httpBasic(Customizer.withDefaults());

        // configuring of Session Management: stateless
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

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

        // Allows restricting access based upon the HttpServletRequest using RequestMatcher implementations
        // (i.e. via URL patterns).
        http.authorizeHttpRequests(auth ->
            auth
                    // 정적 파일 허용
                    // PathRequest: Factory that can be used to create a RequestMatcher for commonly used paths.
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
        );
        setGuestPermission(http);
        setUserPermission(http);
        setAdminPermission(http);
        setStudentPermission(http);
        setTeacherPermission(http);

        // 추후 변경 필요
        /*
         The default is that accessing the URL "/logout" will log the user out by invalidating the HTTP Session,
         cleaning up any rememberMe() authentication that was configured,
         clearing the SecurityContextHolder, and then redirect to "/login?success".
         */
        http.logout(logout -> logout.deleteCookies("JSESSIONID")
                .invalidateHttpSession(false)
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/login?success")
        );

        http.addFilterBefore(exceptionHandlerFilter(), LogoutFilter.class);

        return http.build();
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
                .requestMatchers(HttpMethod.POST, "/auth/logout").hasAnyRole(Role.ADMIN.toString(),
                        Role.STUDENT.toString(),
                        Role.TEACHER.toString())
                .requestMatchers(HttpMethod.GET, "/api/users/*").hasAnyRole(Role.ADMIN.toString(),
                        Role.STUDENT.toString(),
                        Role.TEACHER.toString())
                .requestMatchers(HttpMethod.PATCH, "/api/users/*").hasAnyRole(Role.ADMIN.toString(),
                        Role.STUDENT.toString(),
                        Role.TEACHER.toString())
                .requestMatchers(HttpMethod.GET, "/api/mypage/**").hasAnyRole(Role.ADMIN.toString(),
                        Role.STUDENT.toString(),
                        Role.TEACHER.toString())
                .requestMatchers(HttpMethod.GET, "/api/mypage/**").hasAnyRole(Role.ADMIN.toString(),
                        Role.STUDENT.toString(),
                        Role.TEACHER.toString())
        );
    }

    private void setStudentPermission(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/student/**").hasRole(Role.STUDENT.toString())

        );
    }

    private void setTeacherPermission(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/oficialProfile/*").hasRole(Role.TEACHER.toString())
        );
    }

    private void setAdminPermission(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/users").hasRole(Role.ADMIN.toString())
                .requestMatchers(HttpMethod.DELETE, "/api/users/*").hasRole(Role.ADMIN.toString())
        );
    }
}
