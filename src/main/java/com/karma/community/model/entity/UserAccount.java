package com.karma.community.model.entity;

import com.karma.community.model.util.AuditingFields;
import com.karma.community.model.util.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Table(
        name = "user_account",
        indexes = {
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt")
})
@Entity
public class UserAccount {
    /** Fields
     * username : primary key
     * password : encoded password
     * nickname
     * content
     * articleComments
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 50, unique = true)
    private String username;
    @Setter
    @Column(nullable = false)
    private String password;
    @Setter
    @Column(length = 100, unique = true)
    private String email;
    @Setter
    @Column(length = 100, unique = true)
    private String nickname;
    @Setter
    private String description;
    @Setter
    @Enumerated(EnumType.STRING)
    private RoleType roleType = RoleType.USER;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime modifiedAt;

    protected UserAccount() {
    }

    private UserAccount(
            String username,
            String password,
            String email,
            String nickname,
            String description,
            RoleType roleType,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.description = description;
        this.roleType = roleType;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static UserAccount of(
            String username,
            String password,
            String email,
            String nickname,
            String description
    ) {
        return new UserAccount(
                username,
                password,
                email,
                nickname,
                description,
                RoleType.USER,
                null,
                null
        );
    }

    public static UserAccount of(
            String username,
            String password,
            String email,
            String nickname,
            String description,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        return new UserAccount(
                username,
                password,
                email,
                nickname,
                description,
                RoleType.USER,
                createdAt,
                modifiedAt
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount that)) return false;
        return this.getUserId() != null && this.getUserId().equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId());
    }
}