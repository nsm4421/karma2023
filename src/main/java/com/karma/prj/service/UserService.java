package com.karma.prj.service;

import com.karma.prj.exception.CustomErrorCode;
import com.karma.prj.exception.CustomException;
import com.karma.prj.model.dto.UserDto;
import com.karma.prj.model.entity.UserEntity;
import com.karma.prj.model.util.RoleType;
import com.karma.prj.repository.UserCacheRepository;
import com.karma.prj.repository.UserRepository;
import com.karma.prj.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserCacheRepository userCacheRepository;
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
        // 중복체크
        checkDuplicated(_field.EMAIL, email);
        checkDuplicated(_field.NICKNAME, nickname);
        checkDuplicated(_field.USERNAME, username);
        // 비밀번호 인코딩
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        return UserEntity.dto(userRepository.save(UserEntity.of(email, username, nickname, encodedPassword, RoleType.USER, null, null)));
    }

    /**
     * 로그인 & 캐싱
     * @param username 유저명
     * @param password 비밀번호
     * @return JWT 토큰값
     */
    @Transactional(readOnly = true)
    public String login(String username, String password){
        // 존재하는 회원여부
        UserEntity user = findByUsernameOrElseThrow(username);
        // 비밀번호 일치여부 확인
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw CustomException.of(CustomErrorCode.INVALID_PASSWORD);
        }
        // 캐싱
        userCacheRepository.setUserByUsername(user);
        // JWT 반환
        return JwtUtil.generateToken(username, secretKey, duration);
    }


    /**
     * 중복체크
     * @param f 중복체크할 필드
     * @param value 중복체크할 값
     */
    @Transactional(readOnly = true)
    private void checkDuplicated(_field f, String value){
       switch (f){
           case EMAIL -> {
               // 중복체크 - 유저명, 닉네임, 이메일
               userRepository.findByUsername(value).ifPresent(it->{
                   throw CustomException.of(
                           CustomErrorCode.DUPLICATED_USERNAME,
                           String.format("Username [%s] is duplicated...", value)
                   );
               });
           }
           case USERNAME -> {
               userRepository.findByEmail(value).ifPresent(it->{
                   throw CustomException.of(
                           CustomErrorCode.DUPLICATED_USERNAME,
                           String.format("Email [%s] is duplicated...", value)
                   );
               });
           }
           case NICKNAME -> {
               userRepository.findByNickname(value).ifPresent(it->{
                   throw CustomException.of(
                           CustomErrorCode.DUPLICATED_NICKNAME,
                           String.format("Nickname [%s] is duplicated...", value)
                   );
               });
           }
       }
    }

    /**
     * 중복체크할 필드
     */
    private enum _field{
        USERNAME, NICKNAME, EMAIL;
    }

    @Transactional(readOnly = true)
    public UserEntity findByUsernameOrElseThrow(String username){
        // redis cache 조회 후 없으면
        return userCacheRepository.getUserByUsername(username).orElseGet(
                // DB 조회 후 없으면 에러
                ()-> userRepository.findByUsername(username).orElseThrow(
                        ()->{throw CustomException.of(CustomErrorCode.USERNAME_NOT_FOUND);})
        );
    }
}
