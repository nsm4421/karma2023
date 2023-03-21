package com.karma.community.service;

import com.karma.community.model.dto.UserAccountDto;
import com.karma.community.repository.UserAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserAccountService {
    private UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public Optional<UserAccountDto> findByUsername(String username) {
        return userAccountRepository
                .findById(username)
                .map(UserAccountDto::from);
    }
}
