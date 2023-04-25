package com.karma.myapp.controller;

import com.karma.myapp.controller.request.LoginRequest;
import com.karma.myapp.controller.request.ModifyUserInfoRequest;
import com.karma.myapp.controller.request.SignUpRequest;
import com.karma.myapp.controller.response.CustomResponse;
import com.karma.myapp.controller.response.GetAlarmResponse;
import com.karma.myapp.controller.response.SignUpResponse;
import com.karma.myapp.domain.dto.CustomPrincipal;
import com.karma.myapp.domain.dto.UserAccountDto;
import com.karma.myapp.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    // 알림 가져오기
    @GetMapping("/alarm")
    public CustomResponse<Page<GetAlarmResponse>> getAlarms(
            @AuthenticationPrincipal CustomPrincipal principal,
            @PageableDefault Pageable pageable
    ) {
        return CustomResponse.success(userAccountService.getAlarms(principal, pageable).map(GetAlarmResponse::from));
    }

    // 알림 삭제하기
    @DeleteMapping("/alarm")
    public CustomResponse<Void> getAlarms(
            @AuthenticationPrincipal CustomPrincipal principal,
            @RequestParam(value = "id", required = false) Long id
    ) {
        // 알림 아이디가 없는 경우 → 알림 전체 삭제
        if (id == null) {
            userAccountService.deleteAllAlarm(principal);
        } 
        // 알림 아이디가 있는 경우 → 해당 알림만 삭제
        else {
            userAccountService.deleteAlarm(principal, id);
        }
        return CustomResponse.success();
    }
}