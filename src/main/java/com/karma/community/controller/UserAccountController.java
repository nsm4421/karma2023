package com.karma.community.controller;

import com.karma.community.controller.request.ModifyUserInfoRequest;
import com.karma.community.controller.request.RegisterRequest;
import com.karma.community.model.dto.UserAccountDto;
import com.karma.community.model.util.CustomPrincipal;
import com.karma.community.model.util.CustomResponse;
import com.karma.community.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserAccountController {
    private final UserAccountService userAccountService;

    @PostMapping("/register")
    private ResponseEntity<Object> signUp(
            RegisterRequest req
    ){
        UserAccountDto dto = userAccountService.register(req.dto());
        return CustomResponse.success(dto);
    }

    @PostMapping("/modify")
    private ResponseEntity<Object> modifyUserInfo(
            ModifyUserInfoRequest req,
            @AuthenticationPrincipal CustomPrincipal principal
    ){
        String usernameOfLoginUser = principal.getUsername();
        UserAccountDto dto = userAccountService.modifyUserInfo(req.dto(), usernameOfLoginUser);
        return CustomResponse.success(dto);
    }

    @DeleteMapping
    private ResponseEntity<Object> deleteUser(@AuthenticationPrincipal CustomPrincipal principal){
        userAccountService.deleteUser(principal.getUsername());
        return CustomResponse.success();
    }
}
