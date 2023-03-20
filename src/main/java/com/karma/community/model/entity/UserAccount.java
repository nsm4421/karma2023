package com.karma.community.model.entity;

import com.karma.community.model.util.CustomPrincipal;
import com.karma.community.model.util.RoleType;
import com.karma.community.model.util.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Table(name = "user_account",
        indexes = {
                @Index(columnList = "username", unique = true),
                @Index(columnList = "email", unique = true),
                @Index(columnList = "created_at")
        })
@Entity
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    @Setter
    @Column(nullable = false)
    private String password;
    @Setter
    @Column(length = 100, unique = true)
    private String email;
    @Setter
    @Column(length = 50, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private RoleType roleType = RoleType.USER;

    @Setter @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.ACTIVE;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    private UserAccount(
            String username,
            String password,
            String email,
            String nickname,
            RoleType roleType,
            UserStatus userStatus
    ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.roleType = roleType;
        this.userStatus = userStatus;
    }

    protected UserAccount() {
    }

    public static UserAccount of(
            String username,
            String password,
            String email,
            String nickname,
            RoleType roleType,
            UserStatus userStatus
    ) {
        return new UserAccount(username, password, email, nickname, roleType, userStatus);
    }

    public static UserAccount from(CustomPrincipal principal) {
        return new UserAccount(
                principal.getUsername(),
                principal.getPassword(),
                principal.getEmail(),
                principal.getNickname(),
                principal.getRoleType(),
                principal.getUserStatus()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}