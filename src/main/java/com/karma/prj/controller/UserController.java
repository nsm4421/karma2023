package com.karma.prj.controller;

import com.karma.prj.controller.request.LoginRequest;
import com.karma.prj.controller.request.RegisterRequest;
import com.karma.prj.model.util.CustomResponse;
import com.karma.prj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    /**
     * 회원가입
     * @Param - username nickname email password
     * @Return - nickname
     */
    @PostMapping("/register")
    public CustomResponse<String> register(@RequestBody RegisterRequest req){
        return CustomResponse.success(userService.register(req.getEmail(), req.getUsername(), req.getNickname(), req.getPassword()).getNickname());
    }
    /**
     * 로그인
     * @Param - username password
     * @Return - Authorization token (JWT)
     */
    @PostMapping("/login")
    public CustomResponse<String> login(@RequestBody LoginRequest req){
        return CustomResponse.success(userService.login(req.getUsername(), req.getPassword()));
    }
}
