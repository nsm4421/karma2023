package com.karma.myapp.controller;

import com.karma.myapp.controller.response.CustomResponse;
import com.karma.myapp.controller.response.GetAlarmResponse;
import com.karma.myapp.domain.dto.CustomPrincipal;
import com.karma.myapp.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final UserAccountService userAccountService;

    // 알림 가져오기
    @GetMapping
    public CustomResponse<Page<GetAlarmResponse>> getAlarms(
            @AuthenticationPrincipal CustomPrincipal principal,
            @PageableDefault Pageable pageable
    ) {
        return CustomResponse.success(userAccountService.getAlarms(principal, pageable).map(GetAlarmResponse::from));
    }

    // 알림 삭제하기
    @DeleteMapping
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
