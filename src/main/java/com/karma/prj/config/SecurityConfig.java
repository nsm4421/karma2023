package com.karma.prj.config;

import com.karma.prj.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        // Static(html, css, js, favicon...) 허용
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .permitAll()
                        // TODO : GET, POST 요청 허용할 Url
//                        // GET 요청 허용
//                        .requestMatchers(
//                                HttpMethod.GET,
//                                "/"
//                        )
//                        .permitAll()
//                        // POST 요청 허용
//                        .requestMatchers(
//                                HttpMethod.POST,
//                                "/register", "/login"
//                        )
//                        .permitAll()
                        // 이 외의 모든 기능은 인증 필요
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(withDefaults())
                .logout(logout -> logout.logoutSuccessUrl("/"))
                // csrf 풀기
                .csrf().disable()
                .build();
    }
    @Bean
    public UserDetailsService userDetailsService(UserRepository repository) {
        return null;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
