package com.suehay.jwtsecuritypackage.model.enums;

import lombok.Getter;

import java.util.List;

@Getter
public enum Role {
    ROOT(List.of(Permissions.CREATE_ADMIN, Permissions.DELETE, Permissions.WRITE, Permissions.READ, Permissions.UPDATE)),
    ADMIN(List.of(Permissions.DELETE, Permissions.WRITE, Permissions.READ, Permissions.UPDATE)),
    USER(List.of(Permissions.WRITE, Permissions.READ, Permissions.UPDATE)),
    GUEST(List.of(Permissions.READ));

    final List<Permissions> permissions;

    Role(List<Permissions> permissions) {
        this.permissions = permissions;
    }

    public boolean hasPermission(Permissions permission) {
        return permissions.contains(permission);
    }

    Role getRole(String role) {
        return Role.valueOf(role);
    }
}