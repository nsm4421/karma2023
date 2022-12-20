package com.karma.prj.controller.response;

import lombok.Getter;

import java.util.Map;

@Getter
public class GetLikeResponse {
    private Long postId;
    private Long likeCount;
    private Long hateCount;

    private GetLikeResponse(Long postId, Long likeCount, Long hateCount) {
        this.postId = postId;
        this.likeCount = likeCount;
        this.hateCount = hateCount;
    }

    protected GetLikeResponse(){}

    public static GetLikeResponse of(Long postId, Long likeCount, Long hateCount) {
        return new  GetLikeResponse(postId, likeCount, hateCount);
    }

    public static GetLikeResponse from(Long postId, Map<String, Long> map){
        return GetLikeResponse.of(postId, map.get("LIKE"), map.get("HATE"));
    }
}
