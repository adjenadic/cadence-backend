package rs.raf.cadence.userservice.data.enums;

import org.springframework.security.core.GrantedAuthority;

public enum PermissionType implements GrantedAuthority {
    MANAGE_USERNAMES("MANAGE_USERNAMES"),
    MANAGE_PERMISSIONS("MANAGE_PERMISSIONS"),
    MANAGE_USER_DETAILS("MANAGE_USER_DETAILS"),
    DELETE_USERS("DELETE_USERS"),
    DELETE_COMMENTS("DELETE_COMMENTS");

    private final String permission;

    PermissionType(String permission) {
        this.permission = permission;
    }

    @Override
    public String getAuthority() {
        return permission;
    }
}
