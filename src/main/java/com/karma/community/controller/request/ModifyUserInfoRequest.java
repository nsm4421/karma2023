package com.karma.community.controller.request;

import com.karma.community.model.dto.UserAccountDto;
import com.karma.community.model.util.RoleType;
import com.karma.community.model.util.UserStatus;

public class ModifyUserInfoRequest {
    String username;
    String password;
    String nickname;
    String email;
    UserStatus userStatus;

    private ModifyUserInfoRequest(String username, String password, String nickname, String email, UserStatus userStatus) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.userStatus = userStatus;
    }

    public UserAccountDto dto() {
        return new UserAccountDto(
                username,
                password,
                email,
                nickname,
                RoleType.USER,
                userStatus,
                null,
                null
        );
    }
}
