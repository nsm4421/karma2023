package com.karma.community.controller;

import com.karma.community.controller.request.RegisterRequest;
import com.karma.community.model.dto.UserAccountDto;
import com.karma.community.model.util.CustomResponse;
import com.karma.community.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserAccountController {
    private final UserAccountService userAccountService;

    @PostMapping("/register")
    public CustomResponse<String> register(@RequestBody RegisterRequest req){
        UserAccountDto dto = userAccountService.register(
                req.getUsername(),
                req.getPassword(),
                req.getEmail(),
                req.getNickname(),
                req.getDescription()
        );
        return CustomResponse.success(String.format("%s님 가입을 환영합니다.", dto.nickname()));
    }
}
