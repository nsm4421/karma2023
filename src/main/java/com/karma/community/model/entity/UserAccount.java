package com.karma.community.model.entity;

import com.karma.community.model.util.AuditingFields;
import com.karma.community.model.util.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Table(
        name = "user_account",
        indexes = {
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class UserAccount extends AuditingFields {
    /** Fields
     * username : primary key
     * password : encoded password
     * nickname
     * content
     * articleComments
     */
    @Id
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

    protected UserAccount() {
    }

    private UserAccount(
            String username,
            String password,
            String email,
            String nickname,
            String description,
            RoleType roleType,
            String createdBy
    ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.description = description;
        this.roleType = roleType;
        this.createdBy = createdBy;
        this.modifiedBy = createdBy;
    }

    public static UserAccount of(
            String username,
            String userPassword,
            String email,
            String nickname,
            String description
    ) {
        return UserAccount.of(username, userPassword, email, nickname, description, null);
    }

    public static UserAccount of(
            String username,
            String userPassword,
            String email,
            String nickname,
            String description,
            String createdBy
    ) {
        return new UserAccount(
                username,
                userPassword,
                email,
                nickname,
                description,
                RoleType.USER,                  // role type 필드는 고정
                createdBy
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount that)) return false;
        return this.getUsername() != null && this.getUsername().equals(that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUsername());
    }
}