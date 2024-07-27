package rs.raf.userservice.data.enums;

import org.springframework.security.core.GrantedAuthority;

public enum PermissionType implements GrantedAuthority {
    MANAGE_USERNAMES("MANAGE_USERNAMES"),
    MANAGE_PERMISSIONS("MANAGE_PERMISSIONS");

    private final String permission;

    PermissionType(String permission) {
        this.permission = permission;
    }

    @Override
    public String getAuthority() {
        return permission;
    }
}
