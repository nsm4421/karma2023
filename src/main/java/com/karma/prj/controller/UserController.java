package com.karma.prj.controller;

import com.karma.prj.controller.request.RegisterRequest;
import com.karma.prj.controller.response.RegisterResponse;
import com.karma.prj.model.dto.UserDto;
import com.karma.prj.model.util.CustomResponse;
import com.karma.prj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 회원가입
     * @Param - username nickname email password
     */
    public CustomResponse<RegisterResponse> register(@RequestBody RegisterRequest req){
        return CustomResponse.success(RegisterResponse.from(userService.register(req.getEmail(), req.getUsername(), req.getNickname(), req.getPassword())));
    }
}
