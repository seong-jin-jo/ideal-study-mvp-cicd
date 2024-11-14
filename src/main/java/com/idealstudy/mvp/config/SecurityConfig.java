package com.idealstudy.mvp.config;

import com.idealstudy.mvp.enums.member.Role;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
            auth.requestMatchers("/*").permitAll()
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
