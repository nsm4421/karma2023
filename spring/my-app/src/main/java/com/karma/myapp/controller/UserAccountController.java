package com.karma.myapp.controller;

import com.karma.myapp.controller.request.LoginRequest;
import com.karma.myapp.controller.request.ModifyUserInfoRequest;
import com.karma.myapp.controller.request.SignUpRequest;
import com.karma.myapp.controller.response.CustomResponse;
import com.karma.myapp.controller.response.SignUpResponse;
import com.karma.myapp.domain.dto.CustomPrincipal;
import com.karma.myapp.domain.dto.UserAccountDto;
import com.karma.myapp.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserAccountController {
    private final UserAccountService userAccountService;

    // 회원가입
    @PostMapping("/signup")
    public CustomResponse<SignUpResponse> signUp(@RequestBody SignUpRequest req) {
        UserAccountDto dto = userAccountService.signUp(
                req.getUsername(),
                req.getEmail(),
                req.getPassword(),
                req.getMemo()
        );
        return CustomResponse.success(SignUpResponse.from(dto), "sign up success");
    }

    // 로그인
    @PostMapping("/login")
    public CustomResponse<String> login(@RequestBody LoginRequest req) {
        String token = userAccountService.login(
                req.getUsername(),
                req.getPassword()
        );
        return CustomResponse.success(token, "login success");
    }

    // 회원정보 변경
    @PutMapping("/modify")
    public CustomResponse<SignUpResponse> modifyUserInfo(
            @RequestBody ModifyUserInfoRequest req,
            @AuthenticationPrincipal CustomPrincipal principal
    ) {
        return CustomResponse.success(
                SignUpResponse.from(
                        userAccountService.modifyUserInfo(
                                principal,
                                req.getPassword(),
                                req.getEmail(),
                                req.getMemo())),
                "modify user information success");
    }
}