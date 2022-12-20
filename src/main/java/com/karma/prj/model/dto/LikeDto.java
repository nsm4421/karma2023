package com.karma.prj.model.dto;

import com.karma.prj.model.util.LikeType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDto {
    private String username;
    private Long postId;
    private LikeType likeType;

    private LikeDto(String username, Long postId, LikeType likeType) {
        this.username = username;
        this.postId = postId;
        this.likeType = likeType;
    }

    protected LikeDto(){}

    public static LikeDto of(String username, Long postId, LikeType likeType) {
        return new LikeDto(username, postId, likeType);
    }
}
