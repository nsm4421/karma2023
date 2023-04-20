package com.karma.myapp.controller;

import com.karma.myapp.controller.request.LoginRequest;
import com.karma.myapp.controller.request.SignUpRequest;
import com.karma.myapp.controller.response.CustomResponse;
import com.karma.myapp.domain.dto.UserAccountDto;
import com.karma.myapp.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserAccountController {
    private final UserAccountService userAccountService;

    @PostMapping("/signup")
    public CustomResponse<String> signUp(@RequestBody SignUpRequest req){
        UserAccountDto dto = userAccountService.signUp(
                req.getUsername(),
                req.getEmail(),
                req.getPassword(),
                req.getMemo()
        );
        return CustomResponse.success(dto.username(), "Sign Up Success");
    }

    @PostMapping("/login")
    public CustomResponse<String> login(@RequestBody LoginRequest req){
        String token = userAccountService.login(
                req.getUsername(),
                req.getPassword()
        );
        return CustomResponse.success(token, "login success");
    }
}
