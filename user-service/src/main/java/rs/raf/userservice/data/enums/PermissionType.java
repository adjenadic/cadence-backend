package rs.raf.userservice.data.enums;

import org.springframework.security.core.GrantedAuthority;

public enum PermissionType implements GrantedAuthority {
    PERMISSION_1("PERMISSION_1"),
    PERMISSION_2("PERMISSION_2");

    private final String permission;

    PermissionType(String permission) {
        this.permission = permission;
    }

    @Override
    public String getAuthority() {
        return permission;
    }
}
