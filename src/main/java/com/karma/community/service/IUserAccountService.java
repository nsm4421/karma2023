package com.karma.community.service;

import com.karma.community.model.dto.UserAccountDto;

public interface IUserAccountService {
    UserAccountDto register(UserAccountDto dto);
    UserAccountDto modifyUserInfo(UserAccountDto dto, String usernameOfLoginUser);
    void deleteUser(String username);
}
