package com.karma.prj.controller.request;

import com.karma.prj.model.util.LikeType;
import lombok.Getter;

@Getter
public class LikePostRequest {
    private Long postId;
    private LikeType likeType;
}
