package com.karma.community.model.dto;

import com.karma.community.model.util.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record CustomPrincipal(
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String email,
        String nickname,
        String description
) implements UserDetails {

    public static CustomPrincipal of(String username, String password, String email, String nickname, String description) {
        Set<RoleType> roleTypes = Set.of(RoleType.USER);

        return new CustomPrincipal(
                username,
                password,
                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet())
                ,
                email,
                nickname,
                description
        );
    }

    public static CustomPrincipal from(UserAccountDto dto) {
        return CustomPrincipal.of(
                dto.username(),
                dto.password(),
                dto.email(),
                dto.nickname(),
                dto.description()
        );
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(
                username,
                password,
                email,
                nickname,
                description
        );
    }

    /**
     * Methods to override
     */
    @Override public String getUsername() { return username; }
    @Override public String getPassword() { return password; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}