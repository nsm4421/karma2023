package com.karma.prj.service;

import com.karma.prj.exception.CustomErrorCode;
import com.karma.prj.exception.CustomException;
import com.karma.prj.model.dto.UserDto;
import com.karma.prj.model.entity.UserEntity;
import com.karma.prj.model.util.RoleType;
import com.karma.prj.repository.UserRepository;
import com.karma.prj.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${jwt.secret-key}") private String secretKey;
    @Value("${jwt.duration}") private Long duration;

    /**
     * 회원가입
     * @param email 이메일
     * @param username 유저명
     * @param nickname 닉네임
     * @param password 비밀번호
     * @return 회원가입 성공시 UserDto
     */
    @Transactional
    public UserDto register(String email, String username, String nickname, String password){
        // 중복체크 - 유저명, 닉네임, 이메일
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
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        // DB 저장
        UserEntity user = userRepository.save(UserEntity.of(email, username, nickname, encodedPassword, RoleType.USER, null, null));
        return UserEntity.dto(user);
    }

    /**
     * 로그인
     * @param username 유저명
     * @param password 비밀번호
     * @return JWT 토큰값
     */
    @Transactional(readOnly = true)
    public String login(String username, String password){
        // 존재하는 회원여부
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(()->{throw CustomException.of(CustomErrorCode.USERNAME_NOT_FOUND);});
        // 비밀번호 일치여부 확인
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw CustomException.of(CustomErrorCode.INVALID_PASSWORD);
        }
        return JwtUtil.generateToken(username, secretKey, duration);
    }

    @Transactional(readOnly = true)
    public UserEntity findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(()->{
            throw CustomException.of(CustomErrorCode.USERNAME_NOT_FOUND);
        });
    }
}
