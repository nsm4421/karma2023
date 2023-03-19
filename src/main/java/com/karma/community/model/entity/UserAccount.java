package com.karma.community.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    private UserAccount(String username, String password, String email, String nickname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }

    protected UserAccount() {
    }

    public static UserAccount of(Long username, String password, String email, String nickname) {
        return UserAccount.of(username, password, email, nickname);
    }

    public static UserAccount of(String username, String password, String email, String nickname) {
        return new UserAccount(username, password, email, nickname);
    }
}