package rs.raf.cadence.userservice.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class IdNotFoundException extends UsernameNotFoundException {
    public IdNotFoundException(Long id) {
        super("User with id " + id + " not found.");
    }
}