package com.karma.prj.model.dto;

import com.karma.prj.model.util.NotificationType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotificationDto {
    private Long id;
    private UserDto user;
    private PostDto post;
    private NotificationType notificationType;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime removedAt;

    private NotificationDto(Long id, UserDto user, PostDto post, NotificationType notificationType, String message, LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime removedAt) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.notificationType = notificationType;
        this.message = message;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.removedAt = removedAt;
    }

    protected NotificationDto(){}

    public static NotificationDto of(Long id, UserDto user, PostDto post, NotificationType notificationType, String message, LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime removedAt) {
        return new NotificationDto(id, user, post, notificationType, message, createdAt, modifiedAt, removedAt);
    }
}