package com.karma.prj.model.dto;

import com.karma.prj.model.entity.UserEntity;
import com.karma.prj.model.util.NotificationType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotificationDto {
    private Long id;
    private UserDto user;
    private NotificationType notificationType;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime removedAt;

    private NotificationDto(Long id, UserDto user, NotificationType notificationType, String message, LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime removedAt) {
        this.id = id;
        this.user = user;
        this.notificationType = notificationType;
        this.message = message;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.removedAt = removedAt;
    }

    protected NotificationDto(){}

    public static NotificationDto of(Long id, UserDto user, NotificationType notificationType, String message, LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime removedAt) {
       return new NotificationDto(id, user, notificationType, message, createdAt, modifiedAt, removedAt);
    }
}
