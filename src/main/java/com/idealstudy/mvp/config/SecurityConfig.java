package com.idealstudy.mvp.config;

import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.error.ExceptionHandlerFilter;
import com.idealstudy.mvp.security.filter.FormLoginAuthenticationFilter;
import com.idealstudy.mvp.security.filter.BasicLoginAuthenticationFilter;
import com.idealstudy.mvp.security.filter.JsonLoginAuthenticationFilter;
import com.idealstudy.mvp.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@Log4j2
@RequiredArgsConstructor
public class SecurityConfig {

    // 추후 Spring Security에서 제공하는 Jwt 라이브러리로 대체될 예정
    @Autowired
    private final JwtUtil jwtUtil;
    @Autowired
    private final UserDetailsService userDetailsService;

    @Value("${server.dev}")
    private String isDev;

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

        // JwtAuthenticationProvider가 들어갈 자리(JWT token 처리기)
        
        return new ProviderManager(providers);
    }

    @Bean
    public ExceptionHandlerFilter exceptionHandlerFilter() {
        return new ExceptionHandlerFilter();
    }

    @Bean
    public JsonLoginAuthenticationFilter jsonLoginAuthenticationFilter() {
        JsonLoginAuthenticationFilter filter = new JsonLoginAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(userDetailsService, passwordEncoder()));
        return filter;
    }

    // @Bean
    public BasicLoginAuthenticationFilter loginAuthenticationFilter() {
        BasicLoginAuthenticationFilter filter = new BasicLoginAuthenticationFilter(authenticationManager(
                userDetailsService, passwordEncoder()), jwtUtil);

        return filter;
    }

    // @Bean
    public FormLoginAuthenticationFilter formLoginAuthenticationFilter() {
        FormLoginAuthenticationFilter filter = new FormLoginAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(userDetailsService, passwordEncoder()));
        return filter;
    }

    // OAuth2LoginAuthenticationFilter 생성 및 설정 필요


    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {

        // Enables CSRF protection. This is activated by default when using EnableWebSecurity.
        // CSRF token 사용 시 POST 요청만 가능.
        http.csrf(AbstractHttpConfigurer::disable);

        // Disable HTTP Basic authentication.
        // Disable Form type login.
        http.httpBasic(AbstractHttpConfigurer::disable)
               .formLogin(AbstractHttpConfigurer::disable);

        // configuring of Session Management: stateless
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        if(isDev.equals("true")) {
            http.headers(headers -> headers
                    // Disables Strict Transport Security
                    .httpStrictTransportSecurity(HeadersConfigurer.HstsConfig::disable)
            );
        }

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
                    .requestMatchers(HttpMethod.GET, "/favicon.ico").permitAll()
                    .requestMatchers("/error").permitAll()
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

        /*
         Allows adding a Filter before one of the known Filter classes.
         The known Filter instances are either a Filter listed in HttpSecurityBuilder.addFilter(Filter)
         or a Filter that has already been added using HttpSecurityBuilder.addFilterAfter(Filter, Class)
         or HttpSecurityBuilder.addFilterBefore(Filter, Class).
         */
        http.addFilterBefore(exceptionHandlerFilter(), LogoutFilter.class);
        // http.addFilterBefore(loginAuthenticationFilter(), BasicAuthenticationFilter.class);
        // http.addFilterBefore(formLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jsonLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private void setGuestPermission(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                // This matcher will use the same rules that Spring MVC uses for matching.
                .requestMatchers(HttpMethod.GET, "/").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/offcialProfile/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/sign-up").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users/email-authentication").permitAll()
                .requestMatchers(HttpMethod.GET, "/auth/loginView.html").permitAll()
        );
    }

    private void setUserPermission(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                // the roles that the user should have at least one of (i.e. ADMIN, USER, etc).
                // Each role should not start with ROLE_ since it is automatically prepended already
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
