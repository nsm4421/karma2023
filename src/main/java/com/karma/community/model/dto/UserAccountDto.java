package com.karma.community.model.dto;

import com.karma.community.model.entity.UserAccount;

import java.time.LocalDateTime;

public record UserAccountDto(
        String username,
        String password,
        String email,
        String nickname,
        String description,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static UserAccountDto of(
            String username,
            String password,
            String email,
            String nickname,
            String description
    ) {
        return new UserAccountDto(
                username,
                password,
                email,
                nickname,
                description,
                null,
                null,
                null,
                null
        );
    }

    public static UserAccountDto of(
            String username,
            String password,
            String email,
            String nickname,
            String description,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime modifiedAt,
            String modifiedBy
    ) {
        return new UserAccountDto(username, password, email, nickname, description, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static UserAccountDto from(UserAccount entity) {
        return new UserAccountDto(
                entity.getUsername(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getNickname(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public UserAccount toEntity() {
        return UserAccount.of(
                username,
                password,
                email,
                nickname,
                description
        );
    }
}