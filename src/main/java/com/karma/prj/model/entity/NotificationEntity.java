package com.karma.prj.model.entity;

import com.karma.prj.model.dto.NotificationDto;
import com.karma.prj.model.dto.UserDto;
import com.karma.prj.model.util.AuditingFields;
import com.karma.prj.model.util.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class NotificationEntity extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "user_id")
    private UserEntity user;
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
    @Column(columnDefinition = "TEXT")
    private String message;

    private NotificationEntity(UserEntity user, NotificationType notificationType, String message) {
        this.user = user;
        this.notificationType = notificationType;
        this.message = message;
    }

    protected NotificationEntity(){}

    public static NotificationEntity of(UserEntity user, NotificationType notificationType, String message) {
        return new NotificationEntity(user, notificationType, message);
    }

    public static NotificationDto dto(NotificationEntity entity) {
        return NotificationDto.of(
                entity.getId(),
                UserEntity.dto(entity.getUser()),
                entity.getNotificationType(),
                entity.getMessage(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getRemovedAt()
        );
    }
}
