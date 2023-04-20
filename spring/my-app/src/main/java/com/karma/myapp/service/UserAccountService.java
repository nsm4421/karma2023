package com.karma.myapp.service;

import com.karma.myapp.domain.dto.CustomPrincipal;
import com.karma.myapp.domain.dto.UserAccountDto;
import com.karma.myapp.domain.entity.UserAccountEntity;
import com.karma.myapp.exception.CustomErrorCode;
import com.karma.myapp.exception.CustomException;
import com.karma.myapp.repository.UserAccountRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;


@Service
@RequiredArgsConstructor
@Transactional
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    // JWT 비밀키
    @Value("${jwt.secret-key}")
    private String secretKey;
    // JWT 만료 시간
    @Value("${jwt.expire-ms}")
    private Long expireMs;


    /**
     * Email 회원가입
     *
     * @param username 유저명
     * @param email    이메일
     * @param password (raw)비밀번호
     * @param memo     메모
     * @return DTO
     */
    public UserAccountDto signUp(String username, String email, String password, String memo) {
        // check duplicated or not
        userAccountRepository.findByUsername(username).ifPresent(it -> {
            throw CustomException.of(CustomErrorCode.DUPLICATED_USER_INFO, String.format("Username is duplicated - %s", username));
        });
        userAccountRepository.findByEmail(email).ifPresent(it -> {
            throw CustomException.of(CustomErrorCode.DUPLICATED_USER_INFO, String.format("Email is duplicated - %s", email));
        });
        // save
        return UserAccountDto.from(userAccountRepository.save(
                UserAccountEntity.of(
                        username,
                        email,
                        passwordEncoder.encode(password),
                        memo
                )
        ));
    }

    /**
     * 로그인
     *
     * @param username 유저명
     * @param password (raw) 비밀전호
     * @return token(JWT)
     */
    public String login(String username, String password) {
        // check user exist
        UserAccountEntity entity = userAccountRepository.findByUsername(username).orElseThrow(() -> {
                    throw CustomException.of(CustomErrorCode.ENTITY_NOT_FOUND, String.format("Username not exists - %s", username));
                }
        );

        // check password
        if (!passwordEncoder.matches(password, entity.getPassword())) {
            throw CustomException.of(CustomErrorCode.INVALID_PASSWORD, "Password is wrong");
        }

        // create access token
        Claims claims = Jwts.claims();
        claims.put("username", username);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireMs))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    @Transactional(readOnly = true)
    public CustomPrincipal loadByUsername(String username) {
        return CustomPrincipal.from(
                userAccountRepository.findByUsername(username).orElseThrow(() -> {
                    throw new UsernameNotFoundException(String.format("Username %s is not founded", username));
                }));
    }
}
