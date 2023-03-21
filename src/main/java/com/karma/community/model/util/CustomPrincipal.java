package com.karma.community.model.util;

import com.karma.community.model.entity.UserAccount;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class CustomPrincipal implements UserDetails {
    private String username;
    private String password;
    private String email;
    private String nickname;
    private RoleType roleType;
    private UserStatus userStatus;
    private Collection<? extends GrantedAuthority> authorities;

    private CustomPrincipal(
            String username,
            String password,
            String email,
            String nickname,
            UserStatus userStatus,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.userStatus = userStatus;
        this.authorities = authorities;
    }

    protected CustomPrincipal() {
    }

    public static CustomPrincipal of(
            String username,
            String password,
            String email,
            String nickname,
            UserStatus userStatus
    ) {
        return new CustomPrincipal(
                username,
                password,
                email,
                nickname,
                userStatus,
                Stream.of(RoleType.USER)
                        .map(RoleType::getDescription)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

    public static CustomPrincipal from(UserAccount userAccount){
        return CustomPrincipal.of(
                userAccount.getUsername(),
                userAccount.getPassword(),
                userAccount.getEmail(),
                userAccount.getNickname(),
                userAccount.getUserStatus()
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return !userStatus.equals(UserStatus.REMOVED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !userStatus.equals(UserStatus.BLOCKED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userStatus.equals(UserStatus.ACTIVE) || userStatus.equals(UserStatus.DEACTIVATED);
    }
}
