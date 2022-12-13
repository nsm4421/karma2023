package com.karma.prj.model.entity;

import com.karma.prj.model.dto.UserDto;
import com.karma.prj.model.util.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.management.relation.Role;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "\"user\"")
@SQLDelete(sql = "UPDATE \"user\" SET removed_at = NOW() WHERE id=?")
@Where(clause = "removed_at is NULL")
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String nickname;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleType role;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @CreatedDate @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @LastModifiedDate @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
    @Column(name = "removed_at") @Setter
    private LocalDateTime removedAt;

    private UserEntity(String email, String username, String nickname, String password, RoleType role, LocalDateTime createdAt, LocalDateTime removedAt) {
        this.email = email;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.removedAt = removedAt;
    }

    protected UserEntity(){}

    public static UserEntity of(String email, String username, String nickname, String password, RoleType role, LocalDateTime createdAt, LocalDateTime removedAt) {
        return new UserEntity(email, username, nickname, password, role, createdAt, removedAt);
    }

    public static UserDto dto(UserEntity entity){
        return UserDto.of(
                entity.getEmail(),
                entity.getUsername(),
                entity.getNickname(),
                entity.getPassword(),
                entity.getRole(),
                entity.getCreatedAt(),
                entity.getRemovedAt()
        );
    }
}
