package com.karma.community.model.dto;

import com.karma.community.model.entity.UserAccount;

import java.time.LocalDateTime;

public record UserAccountDto(
        String username,
        String password,
        String email,
        String nickname,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static UserAccountDto from(UserAccount entity) {
        return new UserAccountDto(
                entity.getUsername(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getNickname(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }
}
