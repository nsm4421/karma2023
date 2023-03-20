package com.karma.community.controller.request;

import com.karma.community.model.dto.UserAccountDto;
import com.karma.community.model.util.RoleType;
import com.karma.community.model.util.UserStatus;
import lombok.Data;

@Data
public class RegisterRequest {
    String username;
    String password;
    String nickname;
    String email;

    private RegisterRequest(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    public UserAccountDto dto() {
        return new UserAccountDto(
                username,
                password,
                email,
                nickname,
                RoleType.USER,
                UserStatus.ACTIVE,
                null,
                null
        );
    }
}
