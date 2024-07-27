package rs.raf.userservice.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUsernameNotFoundException extends UsernameNotFoundException {
    public AppUsernameNotFoundException(String username) {
        super("User with username " + username + " not found.");
    }
}