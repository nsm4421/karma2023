package com.karma.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {
    @Bean
    public AuditorAware<String> auditorAware() {
        // TODO : 인증기능 구현 후 createdby, modifiedby 컬럼에 들어갈 정보 수정
        return () -> Optional.of("TODO");
    }
}
