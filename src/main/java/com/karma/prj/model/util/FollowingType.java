package com.karma.prj.model.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FollowingType {
    FOLLOWING("특정 유저가 팔로우하는 유저 반환"),
    FOLLOWED("특정 유저를 팔로우하는 유저 반환");
    private final String description;
}
