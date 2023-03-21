package com.karma.community.service;

import com.karma.community.exception.CustomError;
import com.karma.community.exception.CustomErrorCode;
import com.karma.community.model.dto.UserAccountDto;
import com.karma.community.model.entity.UserAccount;
import com.karma.community.model.util.RoleType;
import com.karma.community.model.util.UserStatus;
import com.karma.community.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountService implements IUserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    /** 회원가입 */
    @Override
    public UserAccountDto register(UserAccountDto dto) {
        checkDuplicatedOrNot(dto.username(), dto.nickname(), dto.email());
        UserAccount user = UserAccount.of(
                dto.username(),
                passwordEncoder.encode(dto.password()),     // password encoding
                dto.email(),
                dto.nickname(),
                RoleType.USER,                              // RoleType, UserStatus 필드는 고정
                UserStatus.ACTIVE
        );
        user.setCreatedAt(LocalDateTime.now());
        user.setModifiedAt(LocalDateTime.now());
        return UserAccountDto.from(userAccountRepository.save(user));
    }

    /** 회원정보 변경 */
    @Override
    public UserAccountDto modifyUserInfo(UserAccountDto dto, String usernameOfLoginUser) {
        if (!dto.username().equals(usernameOfLoginUser)){
            throw CustomError.of(CustomErrorCode.UNAUTHORIZED_ACCESS);
        }
        UserAccount user = UserAccount.of(
                dto.username(),
                dto.password(),
                dto.email(),
                dto.nickname(),
                RoleType.USER,
                dto.userStatus()
        );
        user.setModifiedAt(LocalDateTime.now());
        return UserAccountDto.from(userAccountRepository.save(user));
    }

    /** 회원탈퇴 */
    @Override
    public void deleteUser(String username) {
        userAccountRepository.deleteByUsername(username);
    }

    public Optional<UserAccount> findByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }

    private void checkDuplicatedOrNot(String username, String nickname, String email){
        userAccountRepository.findByUsername(username).ifPresent(it->{
            throw CustomError.of(CustomErrorCode.DUPLICATED_USERNAME);
        });
        userAccountRepository.findByNickname(nickname).ifPresent(it->{
            throw CustomError.of(CustomErrorCode.DUPLICATED_NICKNAME);
        });
        userAccountRepository.findByEmail(email).ifPresent(it->{
            throw CustomError.of(CustomErrorCode.DUPLICATED_EMAIL);
        });
    }
}
