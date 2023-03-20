package com.karma.community.service;

import com.karma.community.exception.CustomError;
import com.karma.community.exception.CustomErrorCode;
import com.karma.community.model.dto.UserAccountDto;
import com.karma.community.model.entity.UserAccount;
import com.karma.community.model.util.RoleType;
import com.karma.community.model.util.UserStatus;
import com.karma.community.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountService implements IUserAccountService {
    private final UserAccountRepository userAccountRepository;

    /** 회원가입 */
    @Override
    public UserAccountDto register(UserAccountDto dto) {
        // TODO : 중복체크
        return UserAccountDto.from(
                userAccountRepository.save(
                        UserAccount.of(
                                dto.username(),
                                dto.password(),
                                dto.email(),
                                dto.nickname(),
                                RoleType.USER,
                                UserStatus.ACTIVE
                        )
                )
        );
    }

    /** 회원정보 변경 */
    @Override
    public UserAccountDto modifyUserInfo(UserAccountDto dto, String usernameOfLoginUser) {
        if (!dto.username().equals(usernameOfLoginUser)){
            throw CustomError.of(CustomErrorCode.UNAUTHORIZED_ACCESS);
        }
        return UserAccountDto.from(userAccountRepository.save(
                UserAccount.of(
                        dto.username(),
                        dto.password(),
                        dto.email(),
                        dto.nickname(),
                        RoleType.USER,
                        dto.userStatus()
                )
        ));
    }

    /** 회원탈퇴 */
    @Override
    public void deleteUser(String username) {
        userAccountRepository.deleteByUsername(username);
    }

    public Optional<UserAccount> findByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }
}
