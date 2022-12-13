package com.karma.prj.controller.response;

import com.karma.prj.model.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {
    private String username;
    private String nickname;

    private RegisterResponse(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    private RegisterResponse(){}

    public static RegisterResponse from(UserDto dto){
        return new RegisterResponse(dto.getUsername(), dto.getUsername());
    }
}
