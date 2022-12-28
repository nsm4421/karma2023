package com.karma.prj.controller;

import com.karma.prj.controller.request.LoginRequest;
import com.karma.prj.controller.request.RegisterRequest;
import com.karma.prj.controller.response.GetNotificationResponse;
import com.karma.prj.exception.CustomErrorCode;
import com.karma.prj.exception.CustomException;
import com.karma.prj.model.entity.UserEntity;
import com.karma.prj.model.util.CustomResponse;
import com.karma.prj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 알람 가져오기
     * @param authentication 인증 context
     * @param pageable 페이지
     * @return GetNotificationResponse Page
     */
    @GetMapping("/notification")
    public CustomResponse<Page<GetNotificationResponse>> getNotification(
            Authentication authentication,
            @PageableDefault Pageable pageable
    ){
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return CustomResponse.success(userService.getNotification(user.getId(), pageable).map(GetNotificationResponse::from));
    }

    /**
     * 알림 삭제하기
     * @param authentication 인증 context
     * @param notificationId 삭제할 알림 id
     * @return 삭제한 알림 id
     */
    @DeleteMapping("/notification/{notificationId}")
    public CustomResponse<Void> deleteNotification(
            Authentication authentication,
            @PathVariable Long notificationId
    ){
        UserEntity user = (UserEntity) authentication.getPrincipal();
        userService.deleteNotificationById(user.getId(), notificationId);
        return CustomResponse.success();
    }

    /**
     * 알림 전체 삭제하기
     * @param authentication 인증 context
     * @return 삭제한 알림 id
     */
    @DeleteMapping("/notification")
    public CustomResponse<Void> deleteAllNotification(
            Authentication authentication
    ){
        UserEntity user = (UserEntity) authentication.getPrincipal();
        userService.deleteAllNotification(user.getId());
        return CustomResponse.success();
    }
}
