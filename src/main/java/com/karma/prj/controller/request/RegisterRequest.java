package com.karma.prj.controller.request;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String email;
    private String username;
    private String nickname;
    private String password;
}
