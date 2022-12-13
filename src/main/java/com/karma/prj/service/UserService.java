package com.karma.prj.service;

import com.karma.prj.exception.CustomErrorCode;
import com.karma.prj.exception.CustomException;
import com.karma.prj.model.dto.UserDto;
import com.karma.prj.model.entity.UserEntity;
import com.karma.prj.model.util.RoleType;
import com.karma.prj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    public UserDto register(String email, String username, String nickname, String password){
        // 중복체크
        userRepository.findByUsername(username).ifPresent(it->{
            throw CustomException.of(
                    CustomErrorCode.DUPLICATED_USERNAME,
                    String.format("Username [%s] is duplicated...", username)
            );
        });
        userRepository.findByNickname(nickname).ifPresent(it->{
            throw CustomException.of(
                    CustomErrorCode.DUPLICATED_USERNAME,
                    String.format("Nickname [%s] is duplicated...", nickname)
            );
        });
        userRepository.findByEmail(email).ifPresent(it->{
            throw CustomException.of(
                    CustomErrorCode.DUPLICATED_USERNAME,
                    String.format("Email [%s] is duplicated...", email)
            );
        });
        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(password);
        // DB 저장
        UserEntity user = userRepository.save(UserEntity.of(email, username, nickname, encodedPassword, RoleType.USER, null, null));
        return UserEntity.dto(user);
    }


}
