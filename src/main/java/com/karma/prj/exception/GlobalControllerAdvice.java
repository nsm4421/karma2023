package com.karma.prj.exception;

import com.karma.prj.model.util.CustomResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> applicationHandler(CustomException error){
        log.error("Error occurs... {}", error.toString());
        return ResponseEntity.status(error.getCode().getStatus())
                .body(CustomResponse.error(error.getCode().name()));
    }
}
