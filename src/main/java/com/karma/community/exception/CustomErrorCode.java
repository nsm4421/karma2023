package com.karma.community.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CustomErrorCode {

    DUPLICATED_USERNAME(HttpStatus.CONFLICT, "중복된 유저명입니다"),
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임입니다"),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다"),
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시물입니다"),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "인증되지 않은 접근입니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다")
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
