package com.karma.community.config;

import com.karma.community.exception.CustomError;
import com.karma.community.exception.CustomErrorCode;
import com.karma.community.model.dto.CustomPrincipal;
import com.karma.community.service.UserAccountService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        // static(html,css,js,favicon...) → permit all
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .permitAll()
                        // 회원가입 → permit all
                        .requestMatchers("/api/user/register")
                        .permitAll()
                        // index 페이지, 게시글 조회 →  Get 요청 허용하는 url
                        .requestMatchers(
                                HttpMethod.GET,
                                "/",
                                "/api/article"
                        ).permitAll()
                        // else → 인증기능 활성화
                        .anyRequest().authenticated()
                )
                // 로그인
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/api/login")
                .and()
                // 로그아웃 시 쿠키 제거
                .logout(logout -> logout.logoutSuccessUrl("/").deleteCookies("JSESSIONID"))
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserAccountService userAccountService) {
        return username -> userAccountService
                .findByUsername(username)
                .map(CustomPrincipal::from)
                .orElseThrow(() -> {
                    throw CustomError.of(
                            CustomErrorCode.USER_NOT_FOUND,
                            String.format("유저명[ %s]는 존재하지 않는 유저명입니다...", username)
                    );
                });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
