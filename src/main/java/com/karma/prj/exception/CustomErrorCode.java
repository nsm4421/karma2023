package com.karma.prj.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    DUPLICATED_USERNAME(HttpStatus.CONFLICT, "User is duplicated..."),
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "Nickaname is duplicated..."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "Email is duplicated...");
    private final HttpStatus status;
    private final String message;
}
