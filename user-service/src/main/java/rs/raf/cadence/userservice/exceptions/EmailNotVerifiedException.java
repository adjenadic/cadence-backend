package rs.raf.cadence.userservice.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class EmailNotVerifiedException extends UsernameNotFoundException {
    public EmailNotVerifiedException(String email) {
        super("User with email " + email + " is not verified. Please verify your email before logging in.");
    }
}
