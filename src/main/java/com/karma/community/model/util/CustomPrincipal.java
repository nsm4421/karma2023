package com.karma.community.model.util;

import com.karma.community.model.dto.UserAccountDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public record CustomPrincipal(
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String email,
        String nickname,
        String description,
        Map<String, Object> oAuth2Attributes
) implements UserDetails, OAuth2User {

    public static CustomPrincipal of(
            String username,
            String password,
            String email,
            String nickname,
            String description
    ) {
        return CustomPrincipal.of(
                username,
                password,
                email,
                nickname,
                description,
                Map.of()
        );
    }

    public static CustomPrincipal of(
            String username,
            String password,
            String email,
            String nickname,
            String description,
            Map<String, Object> oAuth2Attributes
    ) {
        Set<RoleType> roleTypes = Set.of(RoleType.USER);
        return new CustomPrincipal(
                username,
                password,
                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet()),
                email,
                nickname,
                description,
                oAuth2Attributes
        );
    }

    /** DTO → Principal */
    public static CustomPrincipal from(UserAccountDto dto) {
        return CustomPrincipal.of(
                dto.username(),
                dto.password(),
                dto.email(),
                dto.nickname(),
                dto.description()
        );
    }

    /** Principal → DTO */
    public UserAccountDto toDto() {
        return UserAccountDto.of(
                username,
                password,
                email,
                nickname,
                description
        );
    }

    /** methods to override */
    @Override public Map<String, Object> getAttributes() { return oAuth2Attributes; }
    @Override public String getName() {return username;}
    // TODO : User Status 컬럼을 만들고, 아래 method
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}