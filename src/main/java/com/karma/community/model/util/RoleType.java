package com.karma.community.model.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    ADMIN("ROLE_ADMIN"), MANAGER("ROLE_MANAGER"), USER("ROLE_USER");
    final String description;
}
