package com.karma.myapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "Entity not found"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "password is wrong"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token is invalid"),
    DUPLICATED_USER_INFO(HttpStatus.CONFLICT,"User information is wrong");
    private final HttpStatus httpStatus;
    private final String message;
}
