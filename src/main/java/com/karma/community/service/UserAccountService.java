package com.karma.community.service;

import com.karma.community.model.dto.UserAccountDto;
import com.karma.community.model.entity.UserAccount;
import com.karma.community.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<UserAccountDto> findByUsername(String username) {
        return userAccountRepository.findByUsername(username)
                .map(UserAccountDto::from);
    }

    public UserAccountDto register(
            String username,
            String password,
            String email,
            String nickname,
            String description
    ) {
        return UserAccountDto.from(
                userAccountRepository.save(UserAccount.of(
                        username,
                        passwordEncoder.encode(password),    // save encrypted password
                        email,
                        nickname,
                        description,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ))
        );
    }

}
