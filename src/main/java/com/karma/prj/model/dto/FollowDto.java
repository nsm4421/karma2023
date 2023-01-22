package com.karma.prj.model.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class FollowDto {
    private UserDto userFollowed;
    private UserDto userFollowing;

    private FollowDto(UserDto userFollowed, UserDto userFollowing) {
        this.userFollowed = userFollowed;
        this.userFollowing = userFollowing;
    }

    protected FollowDto(){}

    public static FollowDto of(UserDto userFollowed, UserDto userFollowing){
        return new FollowDto(userFollowed, userFollowing);
    }
}
