package com.karma.community.model.util;

import com.karma.community.exception.CustomErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class CustomResponse {

    private static ResponseEntity<Object> of(HttpStatus status, String message, Object data) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", status.value());
        map.put("message", message);
        map.put("data", data);
        return new ResponseEntity<Object>(map, status);
    }

    public static ResponseEntity<Object> success() {
        return CustomResponse.of(HttpStatus.OK, null, null);
    }

    public static ResponseEntity<Object> success(Object data) {
        return CustomResponse.of(HttpStatus.OK, null, data);
    }

    public static ResponseEntity<Object> success(String message, Object data) {
        return CustomResponse.of(HttpStatus.OK, message, data);
    }

    public static ResponseEntity<Object> error(CustomErrorCode errorCode) {
        return CustomResponse.of(errorCode.getHttpStatus(), errorCode.getMessage(), null);
    }

    public static ResponseEntity<Object> error(CustomErrorCode errorCode, String message) {
        return CustomResponse.of(errorCode.getHttpStatus(), message, null);
    }
}