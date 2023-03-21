package com.karma.community.service;

import com.karma.community.model.dto.UserAccountDto;
import com.karma.community.model.entity.UserAccount;
import com.karma.community.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Optional<UserAccountDto> findByUsername(String username) {
        return userAccountRepository.findById(username)
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
                userAccountRepository.save(
                        UserAccount.of(
                                username,
                                passwordEncoder.encode(password),    // save encrypted password
                                email,
                                nickname,
                                description,
                                username
                        )
                )
        );
    }

}
