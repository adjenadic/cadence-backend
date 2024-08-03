package rs.raf.cadence.userservice.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SpringSecurityUtil {
    static public String getPrincipalEmail() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    static public boolean hasPermission(String permission) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(permission));
    }
}
